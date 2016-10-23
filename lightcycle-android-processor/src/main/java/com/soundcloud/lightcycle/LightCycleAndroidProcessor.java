package com.soundcloud.lightcycle;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"com.soundcloud.lightcycle.BaseActivityClass",
        "com.soundcloud.lightcycle.BaseFragmentClass",
        "com.soundcloud.lightcycle.BaseSupportFragmentClass"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LightCycleAndroidProcessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        for (TypeElement baseActivityAnnotation : annotations) {
            throw new IllegalArgumentException("Well we found something");
        }
        return true;
    }
}
