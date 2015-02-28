package com.soundcloud.android.lightcycle;

import com.squareup.javawriter.JavaWriter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("com.soundcloud.android.lightcycle.LightCycle")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LightCycleProcessor extends AbstractProcessor {

    private static final String LIB_PACKAGE = "com.soundcloud.android.lightcycle";
    private static final String ANNOTATION_CLASS = LIB_PACKAGE + ".LightCycle";
    private static final String INJECTOR_CLASS_NAME = "LightCycleInjector";
    private static final String INJECTOR_CLASS = LIB_PACKAGE + "." + INJECTOR_CLASS_NAME;

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
        for (Element lightCycle : annotatedFields) {
            final Element hostElement = lightCycle.getEnclosingElement();
            List<Element> lightCycles = lightCyclesByHostElement.get(hostElement);
            if (lightCycles == null) {
                lightCycles = new LinkedList<>();
                lightCyclesByHostElement.put(hostElement, lightCycles);
            }
            lightCycles.add(lightCycle);
        }

        try {
            for (Map.Entry<Element, List<Element>> entry : lightCyclesByHostElement.entrySet()) {
                generateInjector(entry.getValue(), entry.getKey());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed writing class file", e);
        }

        return true;
    }

    private void generateInjector(List<? extends Element> elements, Element hostElement)
            throws IOException {
        PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(hostElement);
        final String simpleClassName = hostElement.getSimpleName() + "$" + INJECTOR_CLASS_NAME;
        final String qualifiedClassName = packageElement.getQualifiedName() + "." + simpleClassName;

        log("writing class " + qualifiedClassName);
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(
                qualifiedClassName, elements.toArray(new Element[elements.size()]));

        JavaWriter writer = new JavaWriter(sourceFile.openWriter());

        emitClassHeader(packageElement, writer);

        writer.beginType(qualifiedClassName, "class", EnumSet.of(Modifier.PUBLIC, Modifier.FINAL),
                INJECTOR_CLASS + "<" + hostElement + ">");

        emitInjectMethod(elements, hostElement, writer);

        writer.endType();
        writer.close();
    }

    private void emitClassHeader(PackageElement packageElement, JavaWriter writer) throws IOException {
        writer.emitPackage(packageElement.getQualifiedName().toString());
        writer.emitEmptyLine();
    }

    private void emitInjectMethod(List<? extends Element> elements, Element hostElement, JavaWriter writer) throws IOException {
        writer.emitEmptyLine();
        writer.emitAnnotation(Override.class);
        final String hostClass = hostElement.getSimpleName().toString();
        writer.beginMethod("void", "inject", EnumSet.of(Modifier.PUBLIC), hostClass, "target");
        for (Element element : elements) {
            writer.emitStatement("target.attachLightCycle(target.%s)", element.getSimpleName());
        }
        writer.endMethod();
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
