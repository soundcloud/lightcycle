package com.soundcloud.lightcycle;

import com.squareup.javapoet.ClassName;

import javax.annotation.processing.Filer;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

class BaseFragmentClassWriter extends AbstractFragmentClassWriter {
    BaseFragmentClassWriter(Filer filer, DeclaredType fragmentDeclaredType, TypeMirror objectTypeMirror) {
        super(filer, fragmentDeclaredType, objectTypeMirror);
    }

    @Override
    protected ClassName lightCycleDispatcherTypeName() {
        return ClassName.get("com.soundcloud.lightcycle", "FragmentLightCycleDispatcher");
    }

    @Override
    protected ClassName lightCycleTypeName() {
        return ClassName.get("com.soundcloud.lightcycle", "FragmentLightCycle");
    }
}
