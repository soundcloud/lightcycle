package com.soundcloud.android.lightcycle;

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

@SupportedAnnotationTypes("com.soundcloud.android.lightcycle.LightCycle")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LightCycleProcessor extends AbstractProcessor {

    private static final String LIB_PACKAGE = "com.soundcloud.android.lightcycle";
    private static final String ANNOTATION_CLASS = LIB_PACKAGE + ".LightCycle";
    private static final String BINDER_CLASS_NAME = "LightCycleBinder";
    private static final String BINDER_CLASS = LIB_PACKAGE + "." + BINDER_CLASS_NAME;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

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

        log("processing " + annotatedFields.size() + " fields");

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

        log("writing class " + qualifiedClassName);
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
        return name + "$" + BINDER_CLASS_NAME;
    }

    private void emitClassHeader(PackageElement packageElement, JavaWriter writer) throws IOException {
        writer.emitPackage(packageElement.getQualifiedName().toString());
        writer.emitEmptyLine();
    }

    private void emitBindMethod(Set<String> erasedTargetNames, List<? extends Element> elements, Element hostElement, JavaWriter writer) throws IOException {
        writer.emitEmptyLine();
        final String hostClass = hostElement.getSimpleName().toString();
        writer.beginMethod("void", "bind", EnumSet.of(Modifier.PUBLIC, Modifier.STATIC), hostClass, "target");
        emitBindParent(erasedTargetNames, hostElement, writer);
        emitBindLightCycles(elements, writer);
        writer.endMethod();
    }

    private void emitBindLightCycles(List<? extends Element> elements, JavaWriter writer) throws IOException {
        for (Element element : elements) {
            writer.emitStatement("target.bind(target.%s)", element.getSimpleName());
        }
    }

    private void emitBindParent(Set<String> erasedTargetNames, Element hostElement, JavaWriter writer) throws IOException {
        final TypeElement typeElement = (TypeElement) hostElement;

        String parentName = findParent(erasedTargetNames, typeElement);
        if (parentName != null) {
            writer.emitStatement(getBinderName(parentName) + ".bind(target)");
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

    private void log(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "LightCycleProcessor: " + message);
    }
}
