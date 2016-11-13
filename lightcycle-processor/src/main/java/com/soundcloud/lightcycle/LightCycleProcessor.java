package com.soundcloud.lightcycle;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

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
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("com.soundcloud.lightcycle.LightCycle")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LightCycleProcessor extends AbstractProcessor {

    static final String LIB_PACKAGE = "com.soundcloud.lightcycle";
    private static final String ANNOTATION_CLASS = LIB_PACKAGE + ".LightCycle";
    private static final String CLASS_BINDER_NAME = "LightCycleBinder";
    private static final String METHOD_BIND_NAME = "bind";
    private static final String METHOD_BIND_ARGUMENT_NAME = "target";

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
        final String simpleClassName = binderName(hostElement.getSimpleName().toString());
        final String qualifiedClassName = packageElement.getQualifiedName() + "." + simpleClassName;

        note("writing class " + qualifiedClassName);
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(
                qualifiedClassName, elements.toArray(new Element[elements.size()]));

        ClassName hostElementName = ClassName.bestGuess(hostElement.getSimpleName().toString());
        MethodSpec bindMethod = MethodSpec.methodBuilder(METHOD_BIND_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(hostElementName, METHOD_BIND_ARGUMENT_NAME)
                .addCode(generateBindMethod(erasedTargetNames, hostElement, elements))
                .build();

        TypeSpec classType = TypeSpec.classBuilder(simpleClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(bindMethod)
                .build();

        final Writer writer = sourceFile.openWriter();
        JavaFile.builder(packageElement.getQualifiedName().toString(), classType)
                .build()
                .writeTo(writer);
        writer.close();
    }

    private String binderName(String name) {
        return name + "$" + CLASS_BINDER_NAME;
    }

    private CodeBlock generateBindMethod(Set<String> erasedTargetNames, Element hostElement, List<? extends Element> elements) {
        return CodeBlock.builder()
                .add(emitBindParent(erasedTargetNames, hostElement))
                .add(emitBindLightCycles(hostElement, elements))
                .build();
    }

    private CodeBlock emitBindParent(Set<String> erasedTargetNames, Element hostElement) {
        final TypeElement typeElement = (TypeElement) hostElement;

        LightCycleBinder parentName = findParent(erasedTargetNames, typeElement.getSuperclass());
        return parentName.generateBind(processingEnv.getMessager(), hostElement);
    }

    private CodeBlock emitBindLightCycles(Element hostElement, List<? extends Element> elements) {
        final LightCycleBinder binder = findBinder((TypeElement) hostElement);
        CodeBlock.Builder cb = CodeBlock.builder();
        for (Element element : elements) {
            cb.add(binder.generateBind(processingEnv.getMessager(), element));
        }
        return cb.build();
    }

    private LightCycleBinder findBinder(TypeElement element) {
        if (element == null) {
            return LightCycleBinder.DISPATCHER_NOT_FOUND;
        }

        final TypeMirror elementType = element.asType();
        if (elementType.getKind() != TypeKind.DECLARED) {
            return LightCycleBinder.DISPATCHER_NOT_FOUND;
        }

        for (TypeMirror typeMirror : typeUtils.directSupertypes(elementType)) {
            for (LightCycleDispatcherKind dispatcherKind : LightCycleDispatcherKind.values()) {
                if (dispatcherKind.matches(typeUtils.asElement(typeMirror).getSimpleName())) {
                    return LightCycleBinder.forFields(dispatcherKind, ((DeclaredType) typeMirror));
                }
            }
        }

        return findBinder((TypeElement) typeUtils.asElement(element.getSuperclass()));
    }

    private LightCycleBinder findParent(Set<String> erasedTargetNames, TypeMirror type) {
        if (type.getKind() == TypeKind.NONE) {
            return LightCycleBinder.EMPTY;
        }

        final TypeElement typeElement = (TypeElement) ((DeclaredType) type).asElement();
        if (erasedTargetNames.contains(typeElement.toString())) {
            final String parentWithLightCycle = elementUtils.getBinaryName(typeElement).toString();
            return LightCycleBinder.forParent(binderName(parentWithLightCycle));
        }
        return findParent(erasedTargetNames, typeElement.getSuperclass());
    }

    private void verifyFieldsAccessible(Set<? extends Element> elements) {
        for (Element element : elements) {
            if (element.getModifiers().contains(Modifier.PRIVATE)) {
                throw new IllegalStateException("Annotated fields cannot be private: "
                        + element.getEnclosingElement() + "#" + element + "(" + element.asType() + ")");
            }
        }
    }

    private void note(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "LightCycleProcessor: " + message);
    }
}
