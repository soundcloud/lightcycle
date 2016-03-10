package com.soundcloud.lightcycle;

import com.squareup.javawriter.JavaWriter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("com.soundcloud.lightcycle.LightCycle")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LightCycleProcessor extends AbstractProcessor {

    private static final String LIB_PACKAGE = "com.soundcloud.lightcycle";
    private static final String ANNOTATION_CLASS = LIB_PACKAGE + ".LightCycle";
    private static final String CLASS_BINDER_NAME = "LightCycleBinder";
    private static final String METHOD_BIND_NAME = "bind";
    private static final String METHOD_BIND_ARGUMENT_NAME = "target";
    private static final String METHOD_LIFT_NAME = "com.soundcloud.lightcycle.LightCycleBinder.lift";
    private static final String INTERFACE_DISPATCHER_NAME = "LightCycleDispatcher";

    private Elements elementUtils;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        final TypeElement lightCycleAnnotation = processingEnv.getElementUtils().getTypeElement(ANNOTATION_CLASS);
        final Set<? extends Element> annotatedFields = roundEnvironment.getElementsAnnotatedWith(lightCycleAnnotation);
        if (annotatedFields.isEmpty()) {
            return true;
        }

        verifyFieldsAccessible(annotatedFields);

        note("processing " + annotatedFields.size() + " fields");

        Map<Element, List<Element>> lightCyclesByHostElement = new HashMap<>();
        Set<String> erasedTargetNames = new HashSet<>();
        for (Element lightCycle : annotatedFields) {
            final Element hostElement = lightCycle.getEnclosingElement();
            List<Element> lightCycles = lightCyclesByHostElement.get(hostElement);
            if (lightCycles == null) {
                lightCycles = new LinkedList<>();
                erasedTargetNames.add(hostElement.toString());
                lightCyclesByHostElement.put(hostElement, lightCycles);
            }
            lightCycles.add(lightCycle);
        }

        try {
            for (Map.Entry<Element, List<Element>> entry : lightCyclesByHostElement.entrySet()) {
                generateBinder(erasedTargetNames, entry.getValue(), entry.getKey());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed writing class file", e);
        }

        return true;
    }

    private void generateBinder(Set<String> erasedTargetNames, List<? extends Element> elements, Element hostElement)
            throws IOException {
        PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(hostElement);
        final String simpleClassName = getBinderName(hostElement.getSimpleName().toString());
        final String qualifiedClassName = packageElement.getQualifiedName() + "." + simpleClassName;

        note("writing class " + qualifiedClassName);
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(
                qualifiedClassName, elements.toArray(new Element[elements.size()]));

        JavaWriter writer = new JavaWriter(sourceFile.openWriter());

        emitClassHeader(packageElement, writer);

        writer.beginType(qualifiedClassName, "class",
                EnumSet.of(Modifier.PUBLIC, Modifier.FINAL),
                null);

        emitBindMethod(erasedTargetNames, elements, hostElement, writer);

        writer.endType();
        writer.close();
    }

    private String getBinderName(String name) {
        return name + "$" + CLASS_BINDER_NAME;
    }

    private void emitClassHeader(PackageElement packageElement, JavaWriter writer) throws IOException {
        writer.emitPackage(packageElement.getQualifiedName().toString());
        writer.emitEmptyLine();
    }

    private void emitBindMethod(Set<String> erasedTargetNames, List<? extends Element> elements, Element hostElement, JavaWriter writer) throws IOException {
        writer.emitEmptyLine();
        final String hostClass = hostElement.getSimpleName().toString();
        writer.beginMethod("void", METHOD_BIND_NAME, EnumSet.of(Modifier.PUBLIC, Modifier.STATIC), hostClass, METHOD_BIND_ARGUMENT_NAME);
        emitBindParent(erasedTargetNames, hostElement, writer);
        emitBindLightCycles(hostElement, elements, writer);
        writer.endMethod();
    }

    private void emitBindLightCycles(Element hostElement, List<? extends Element> elements, JavaWriter writer) throws IOException {
        final TypeMirror dispatchedType = getDispatchedType((TypeElement) hostElement);
        if (dispatchedType == null) {
            error(hostElement, "Could not find the dispatcher. Class may implement " + INTERFACE_DISPATCHER_NAME);
        } else {
            for (Element element : elements) {
                final String liftedName = element.getSimpleName() + "$Lifted";

                writer.emitStatement("final %s %s = %s(%s.%s)",
                        dispatchedType.toString(),
                        liftedName,
                        METHOD_LIFT_NAME,
                        METHOD_BIND_ARGUMENT_NAME,
                        element.getSimpleName());
                writer.emitStatement("%s.%s(%s)", METHOD_BIND_ARGUMENT_NAME, METHOD_BIND_NAME, liftedName);
            }
        }
    }

    private TypeMirror getDispatchedType(TypeElement hostElement) {
        TypeElement typeElement = hostElement;
        TypeMirror type = hostElement.asType();

        for (; type.getKind() != TypeKind.NONE; ) {
            for (TypeMirror anInterface : typeElement.getInterfaces()) {
                final Element element = typeUtils.asElement(anInterface);
                if (element.getSimpleName().contentEquals(INTERFACE_DISPATCHER_NAME)) {
                    return ((DeclaredType) anInterface).getTypeArguments().get(0);
                }
            }
            typeElement = (TypeElement) ((DeclaredType) type).asElement();
            type = typeElement.getSuperclass();
        }
        return null;
    }

    private void emitBindParent(Set<String> erasedTargetNames, Element hostElement, JavaWriter writer) throws IOException {
        final TypeElement typeElement = (TypeElement) hostElement;

        String parentName = findParent(erasedTargetNames, typeElement);
        if (parentName != null) {
            writer.emitStatement(getBinderName(parentName) + ".%s(%s)", METHOD_BIND_NAME, METHOD_BIND_ARGUMENT_NAME);
        }
    }

    private String findParent(Set<String> erasedTargetNames, TypeElement typeElement) {
        TypeMirror type;
        while (true) {
            type = typeElement.getSuperclass();
            if (type.getKind() == TypeKind.NONE) {
                return null;
            }
            typeElement = (TypeElement) ((DeclaredType) type).asElement();
            if (erasedTargetNames.contains(typeElement.toString())) {
                String packageName = getPackageName(typeElement);
                return packageName + "." + getClassName(typeElement, packageName);
            }
        }
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    private void verifyFieldsAccessible(Set<? extends Element> elements) {
        for (Element element : elements) {
            if (element.getModifiers().contains(Modifier.PRIVATE)) {
                throw new IllegalStateException("Annotated fields cannot be private: " +
                        element.getEnclosingElement() + "#" + element + "(" + element.asType() + ")");
            }
        }
    }

    private void note(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "LightCycleProcessor: " + message);
    }

    private void error(Element element, String message, Object... args) {
        log(Diagnostic.Kind.ERROR, element, message, args);
    }

    private void log(Diagnostic.Kind error, Element element, String message, Object[] args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(error, message, element);
    }
}
