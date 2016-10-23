package com.soundcloud.lightcycle;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

class LightCycleAndroidBaseActivityClassWriter {
    private static final String LIGHT_CYCLE_DISPATCHER_FIELD_NAME = "lightCycleDispatcher";
    private static final String LIGHT_CYCLE_PARAM_NAME = "lightCycle";
    private static final String SAVED_INSTANCE_STATE_PARAM_NAME = "savedInstanceState";
    private static final String INTENT_PARAM_NAME = "intent";
    private static final String ITEM_PARAM_NAME = "item";
    private static final String OUT_STATE_PARAM_NAME = "outState";

    private final Filer filer;
    private final TypeElement activityClass;

    LightCycleAndroidBaseActivityClassWriter(Filer filer, TypeElement activityClass) {
        this.filer = filer;
        this.activityClass = activityClass;
    }

    void write() throws IOException {
        final Writer writer = javaFileObject().openWriter();
        javaFile().writeTo(writer);
        writer.close();
    }

    private JavaFileObject javaFileObject() throws IOException {
        return filer.createSourceFile(String.format("%s.%s", elementPackageName(), typeName()), activityClass);
    }

    private JavaFile javaFile() {
        return JavaFile.builder(elementPackageName(), type()).build();
    }

    private TypeSpec type() {
        return TypeSpec.classBuilder(typeName())
                .addModifiers(Modifier.PUBLIC)
                .superclass(ClassName.get(activityClass))
                .addField(dispatcherField())
                .addMethod(bindMethod())
                .addMethod(onCreateMethod())
                .addMethod(onNewIntentMethod())
                .addMethod(onStartMethod())
                .addMethod(onResumeMethod())
                .addMethod(onOptionsItemSelectedMethod())
                .addMethod(onPauseMethod())
                .addMethod(onStopMethod())
                .addMethod(onSaveInstanceStateMethod())
                .addMethod(onRestoreInstanceStateMethod())
                .addMethod(onDestroyMethod())
                .addMethod(objectMethod())
                .build();
    }

    private FieldSpec dispatcherField() {
        return FieldSpec.builder(objectLightCycleDispatcherTypeName(), LIGHT_CYCLE_DISPATCHER_FIELD_NAME)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T()", objectLightCycleDispatcherTypeName())
                .build();
    }

    private MethodSpec bindMethod() {
        return MethodSpec.methodBuilder("bind")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(objectLightCycleTypeName(), LIGHT_CYCLE_PARAM_NAME)
                .addStatement("$N.bind($N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, LIGHT_CYCLE_PARAM_NAME)
                .build();
    }

    private MethodSpec onCreateMethod() {
        return MethodSpec.methodBuilder("onCreate")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addParameter(bundleTypeName(), SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("super.onCreate($N)", SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("$T.bind(this)", lightCyclesTypeName())
                .addStatement("$N.onCreate($N(), $N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, objectMethodName(),
                        SAVED_INSTANCE_STATE_PARAM_NAME)
                .build();
    }

    private MethodSpec onNewIntentMethod() {
        return MethodSpec.methodBuilder("onNewIntent")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addParameter(intentTypeName(), INTENT_PARAM_NAME)
                .addStatement("super.onNewIntent($N)", INTENT_PARAM_NAME)
                .addStatement("$N.onNewIntent($N(), $N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, objectMethodName(),
                        INTENT_PARAM_NAME)
                .build();
    }

    private MethodSpec onStartMethod() {
        return MethodSpec.methodBuilder("onStart")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addStatement("super.onStart()")
                .addStatement("$N.onStart($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, objectMethodName())
                .build();
    }

    private MethodSpec onResumeMethod() {
        return MethodSpec.methodBuilder("onResume")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addStatement("super.onResume()")
                .addStatement("$N.onResume($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, objectMethodName())
                .build();
    }

    private MethodSpec onOptionsItemSelectedMethod() {
        return MethodSpec.methodBuilder("onOptionsItemSelected")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.BOOLEAN)
                .addParameter(menuItemTypeName(), ITEM_PARAM_NAME)
                .addStatement("return $N.onOptionsItemSelected($N(), $N) || super.onOptionsItemSelected($N)",
                        LIGHT_CYCLE_DISPATCHER_FIELD_NAME, objectMethodName(), ITEM_PARAM_NAME, ITEM_PARAM_NAME)
                .build();
    }

    private MethodSpec onPauseMethod() {
        return MethodSpec.methodBuilder("onPause")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addStatement("$N.onPause($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, objectMethodName())
                .addStatement("super.onPause()")
                .build();
    }

    private MethodSpec onStopMethod() {
        return MethodSpec.methodBuilder("onStop")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addStatement("$N.onStop($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, objectMethodName())
                .addStatement("super.onStop()")
                .build();
    }

    private MethodSpec onSaveInstanceStateMethod() {
        return MethodSpec.methodBuilder("onSaveInstanceState")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addParameter(bundleTypeName(), OUT_STATE_PARAM_NAME)
                .addStatement("super.onSaveInstanceState($N)", OUT_STATE_PARAM_NAME)
                .addStatement("$N.onSaveInstanceState($N(), $N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME,
                        objectMethodName(), OUT_STATE_PARAM_NAME)
                .build();
    }

    private MethodSpec onRestoreInstanceStateMethod() {
        return MethodSpec.methodBuilder("onRestoreInstanceState")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addParameter(bundleTypeName(), SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("super.onRestoreInstanceState($N)", SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("$N.onRestoreInstanceState($N(), $N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME,
                        objectMethodName(), SAVED_INSTANCE_STATE_PARAM_NAME)
                .build();
    }

    private MethodSpec onDestroyMethod() {
        return MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addStatement("$N.onDestroy($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, objectMethodName())
                .addStatement("super.onDestroy()")
                .build();
    }

    private MethodSpec objectMethod() {
        return MethodSpec.methodBuilder(objectMethodName())
                .addAnnotation(suppressUncheckedWarningAnnotation())
                .addModifiers(Modifier.PRIVATE)
                .returns(objectTypeName())
                .addStatement("return ($T) this", objectTypeName())
                .build();
    }

    private AnnotationSpec suppressUncheckedWarningAnnotation() {
        return AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "unchecked")
                .build();
    }

    private ParameterizedTypeName objectLightCycleDispatcherTypeName() {
        return ParameterizedTypeName.get(lightCycleDispatcherTypeName(), objectTypeName());
    }

    private ParameterizedTypeName objectLightCycleTypeName() {
        return ParameterizedTypeName.get(lightCycleTypeName(), objectTypeName());
    }

    private ClassName lightCyclesTypeName() {
        return ClassName.get(LightCycles.class);
    }

    private ClassName bundleTypeName() {
        return ClassName.get("android.os", "Bundle");
    }

    private ClassName intentTypeName() {
        return ClassName.get("android.content", "Intent");
    }

    private ClassName menuItemTypeName() {
        return ClassName.get("android.view", "MenuItem");
    }

    // TODO: Change dispatcher type depending on lightcycle-type
    private ClassName lightCycleDispatcherTypeName() {
        return ClassName.get("com.soundcloud.lightcycle", "ActivityLightCycleDispatcher");
    }

    // TODO: Change dispatcher type depending on lightcycle-type
    private ClassName lightCycleTypeName() {
        return ClassName.get("com.soundcloud.lightcycle", "ActivityLightCycle");
    }

    // TODO: Change dispatcher type depending on lightcycle-type
    private ClassName objectTypeName() {
        return ClassName.get("android.app", "Activity");
    }

    // TODO: Change dispatcher type depending on lightcycle-type
    private String objectMethodName() {
        return "activity";
    }

    private String typeName() {
        return String.format("LightCycle_%s", activityClass.getSimpleName().toString());
    }

    private String elementPackageName() {
        return activityClass.getEnclosingElement().getSimpleName().toString();
    }
}
