package com.soundcloud.lightcycle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.soundcloud.lightcycle.LightCycleBaseClass")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LightCycleAndroidProcessor extends AbstractProcessor {
    private static final String LIGHT_CYCLE_DISPATCHER_NAME = LightCycleDispatcher.class.getName();
    private static final String ACTIVITY_LIGHT_CYCLE_NAME = "com.soundcloud.lightcycle.ActivityLightCycle";
    private static final String FRAGMENT_LIGHT_CYCLE_NAME = "com.soundcloud.lightcycle.FragmentLightCycle";
    private static final String SUPPORT_FRAGMENT_LIGHT_CYCLE_NAME = "com.soundcloud.lightcycle.SupportFragmentLightCycle";
    private static final List<String> LIGHT_CYCLE_NAMES = new ArrayList<>();

    static {
        LIGHT_CYCLE_NAMES.add(ACTIVITY_LIGHT_CYCLE_NAME);
        LIGHT_CYCLE_NAMES.add(FRAGMENT_LIGHT_CYCLE_NAME);
        LIGHT_CYCLE_NAMES.add(SUPPORT_FRAGMENT_LIGHT_CYCLE_NAME);
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        for (TypeElement baseClassAnnotation : annotations) {
            for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(baseClassAnnotation)) {
                handleAnnotatedTypeElement((TypeElement) annotatedElement);
            }
        }
        return true;
    }

    private void handleAnnotatedTypeElement(TypeElement annotatedTypeElement) {
        if (annotatedTypeElement.getNestingKind().isNested()) {
            addError(String.format("%s is nested. LightCycle generated base classes must be on a non-nested class.",
                    annotatedTypeElement));
        } else if (annotatedTypeElement.getKind() != ElementKind.CLASS) {
            addError(String.format("%s is not a class.", annotatedTypeElement));
        } else if (annotatedTypeElement.getModifiers().contains(Modifier.FINAL)) {
            addError(String.format("%s is final. LightCycle generated base classes must be a subclass.",
                    annotatedTypeElement));
        } else if (lightCycleDispatcherDeclaredType(annotatedTypeElement) == null) {
            addError(String.format("%s is not a LightCycleDispatcher. LightCycle generated base classes must " +
                    "implement %s", annotatedTypeElement, LIGHT_CYCLE_DISPATCHER_NAME));
        } else if (!LIGHT_CYCLE_NAMES.contains(lightCycleTypeName(annotatedTypeElement))) {
            addError(String.format("%s must be a LightCycleDispatcher for either %s", annotatedTypeElement,
                    LIGHT_CYCLE_NAMES));
        } else {
            LightCycleAndroidBaseClassWriter baseClassWriter;
            if (lightCycleTypeName(annotatedTypeElement).equals(ACTIVITY_LIGHT_CYCLE_NAME)) {
                baseClassWriter = new LightCycleAndroidBaseActivityClassWriter(processingEnv.getFiler(),
                        (DeclaredType) annotatedTypeElement.asType(),
                        objectTypeMirror(annotatedTypeElement));
            } else {
                baseClassWriter = new LightCycleAndroidBaseFragmentClassWriter(processingEnv.getFiler(),
                        (DeclaredType) annotatedTypeElement.asType(),
                        objectTypeMirror(annotatedTypeElement));
            }
            try {
                baseClassWriter.write();
            } catch (IOException e) {
                addError(e.toString());
            }
        }
    }

    private TypeMirror objectTypeMirror(TypeElement annotatedTypeElement) {
        DeclaredType lightCycleDeclaredType = lightCycleDeclaredType(annotatedTypeElement);
        if (lightCycleDeclaredType == null) {
            return null;
        } else {
            return lightCycleDeclaredType.getTypeArguments().get(0);
        }
    }

    private String lightCycleTypeName(TypeElement annotatedTypeElement) {
        DeclaredType lightCycleDeclaredType = lightCycleDeclaredType(annotatedTypeElement);
        if (lightCycleDeclaredType == null) {
            return null;
        } else {
            return lightCycleDeclaredType.asElement().toString();
        }
    }

    private DeclaredType lightCycleDeclaredType(TypeElement annotatedTypeElement) {
        DeclaredType lightCycleDispatcherDeclaredType = lightCycleDispatcherDeclaredType(annotatedTypeElement);
        if (lightCycleDispatcherDeclaredType == null) {
            return null;
        } else {
            return (DeclaredType) lightCycleDispatcherDeclaredType.getTypeArguments().get(0);
        }
    }

    private DeclaredType lightCycleDispatcherDeclaredType(TypeElement annotatedTypeElement) {
        for (TypeMirror interfaceTypeMirror : annotatedTypeElement.getInterfaces()) {
            DeclaredType interfaceDeclaredType = (DeclaredType) interfaceTypeMirror;
            TypeElement interfaceType = (TypeElement) interfaceDeclaredType.asElement();
            if (interfaceType.toString().equals(LIGHT_CYCLE_DISPATCHER_NAME)) {
                return interfaceDeclaredType;
            }
        }
        return null;
    }

    private void addError(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }
}
