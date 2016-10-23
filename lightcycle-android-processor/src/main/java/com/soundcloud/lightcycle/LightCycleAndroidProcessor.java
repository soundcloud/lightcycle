package com.soundcloud.lightcycle;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.soundcloud.lightcycle.LightCycleBaseClass")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LightCycleAndroidProcessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        for (TypeElement baseClassAnnotation : annotations) {
            for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(baseClassAnnotation)) {
                handleAnnotatedTypeElement(baseClassAnnotation, (TypeElement) annotatedElement);
            }
        }
        return true;
    }

    private void handleAnnotatedTypeElement(Element baseClassAnnotation, TypeElement annotatedTypeElement) {
        if (annotatedTypeElement.getNestingKind().isNested()) {
            addError(String.format("%s is nested. Annotation %s must be on a non-nested class.", annotatedTypeElement,
                    baseClassAnnotation));
        } else if (annotatedTypeElement.getKind() != ElementKind.CLASS) {
            addError(String.format("%s is not a class.", annotatedTypeElement));
        } else if (annotatedTypeElement.getModifiers().contains(Modifier.FINAL)) {
            addError(String.format("%s is final. LightCycle generated base classes must be a subclass.",
                    annotatedTypeElement));
        } else {
            try {
                new LightCycleAndroidBaseActivityClassWriter(processingEnv.getFiler(), annotatedTypeElement).write();
            } catch (IOException e) {
                addError(e.toString());
            }
        }
    }

    private void addError(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }
}
