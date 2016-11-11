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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

abstract class AbstractFragmentClassWriter implements BaseClassWriter {
    private static final String LIGHT_CYCLE_DISPATCHER_FIELD_NAME = "lightCycleDispatcher";
    private static final String BOUND_FIELD_NAME = "bound";
    private static final String LIGHT_CYCLE_PARAM_NAME = "lightCycle";
    private static final String ACTIVITY_PARAM_NAME = "activity";
    private static final String BIND_IF_NECESSARY_METHOD_NAME = "bindIfNecessary";
    private static final String SAVED_INSTANCE_STATE_PARAM_NAME = "savedInstanceState";
    private static final String VIEW_PARAM_NAME = "view";
    private static final String ITEM_PARAM_NAME = "item";
    private static final String OUT_STATE_PARAM_NAME = "outState";
    private static final String OBJECT_METHOD_NAME = "fragment";
    private static final String GENERATED_PREFIX = "LightCycle_";

    private final Filer filer;
    private final DeclaredType fragmentDeclaredType;
    private final TypeMirror objectTypeMirror;

    AbstractFragmentClassWriter(Filer filer, DeclaredType fragmentDeclaredType, TypeMirror objectTypeMirror) {
        this.filer = filer;
        this.fragmentDeclaredType = fragmentDeclaredType;
        this.objectTypeMirror = objectTypeMirror;
    }

    @Override
    public void write() throws IOException {
        final Writer writer = javaFileObject().openWriter();
        javaFile().writeTo(writer);
        writer.close();
    }

    protected abstract ClassName lightCycleDispatcherTypeName();

    protected abstract ClassName lightCycleTypeName();

    private JavaFileObject javaFileObject() throws IOException {
        return filer.createSourceFile(javaFileResourceName(), fragmentType());
    }

    private JavaFile javaFile() {
        return JavaFile.builder(elementPackageName(), type()).build();
    }

    private TypeSpec type() {
        return TypeSpec.classBuilder(typeName())
                .addModifiers(Modifier.PUBLIC)
                .superclass(fragmentTypeName())
                .addField(dispatcherField())
                .addField(boundField())
                .addMethod(bindMethod())
                .addMethod(onAttachMethod())
                .addMethod(onViewCreatedMethod())
                .addMethod(onActivityCreatedMethod())
                .addMethod(onCreateMethod())
                .addMethod(onStartMethod())
                .addMethod(onResumeMethod())
                .addMethod(onOptionsItemSelectedMethod())
                .addMethod(onPauseMethod())
                .addMethod(onStopMethod())
                .addMethod(onSaveInstanceStateMethod())
                .addMethod(onDestroyViewMethod())
                .addMethod(onDestroyMethod())
                .addMethod(onDetachMethod())
                .addMethod(objectMethod())
                .addMethod(bindIfNecessaryMethod())
                .build();
    }

    private FieldSpec dispatcherField() {
        return FieldSpec.builder(objectLightCycleDispatcherTypeName(), LIGHT_CYCLE_DISPATCHER_FIELD_NAME)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T()", objectLightCycleDispatcherTypeName())
                .build();
    }

    private FieldSpec boundField() {
        return FieldSpec.builder(TypeName.BOOLEAN, BOUND_FIELD_NAME)
                .addModifiers(Modifier.PRIVATE)
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

    private MethodSpec onAttachMethod() {
        return MethodSpec.methodBuilder("onAttach")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(activityTypeName(), ACTIVITY_PARAM_NAME)
                .addStatement("super.onAttach($N)", ACTIVITY_PARAM_NAME)
                .addStatement("$N()", BIND_IF_NECESSARY_METHOD_NAME)
                .addStatement("$N.onAttach($N(), $N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME,
                        ACTIVITY_PARAM_NAME)
                .build();
    }

    private MethodSpec onCreateMethod() {
        return MethodSpec.methodBuilder("onCreate")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(bundleTypeName(), SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("super.onCreate($N)", SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("$N.onCreate($N(), $N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME,
                        SAVED_INSTANCE_STATE_PARAM_NAME)
                .build();
    }

    private MethodSpec onViewCreatedMethod() {
        return MethodSpec.methodBuilder("onViewCreated")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewTypeName(), VIEW_PARAM_NAME)
                .addParameter(bundleTypeName(), SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("super.onViewCreated($N, $N)", VIEW_PARAM_NAME, SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("$N.onViewCreated($N(), $N, $N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME,
                        VIEW_PARAM_NAME, SAVED_INSTANCE_STATE_PARAM_NAME)
                .build();
    }

    private MethodSpec onActivityCreatedMethod() {
        return MethodSpec.methodBuilder("onActivityCreated")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(bundleTypeName(), SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("super.onActivityCreated($N)", SAVED_INSTANCE_STATE_PARAM_NAME)
                .addStatement("$N.onActivityCreated($N(), $N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME,
                        SAVED_INSTANCE_STATE_PARAM_NAME)
                .build();
    }

    private MethodSpec onStartMethod() {
        return MethodSpec.methodBuilder("onStart")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super.onStart()")
                .addStatement("$N.onStart($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME)
                .build();
    }

    private MethodSpec onResumeMethod() {
        return MethodSpec.methodBuilder("onResume")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super.onResume()")
                .addStatement("$N.onResume($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME)
                .build();
    }

    private MethodSpec onOptionsItemSelectedMethod() {
        return MethodSpec.methodBuilder("onOptionsItemSelected")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.BOOLEAN)
                .addParameter(menuItemTypeName(), ITEM_PARAM_NAME)
                .addStatement("return $N.onOptionsItemSelected($N(), $N) || super.onOptionsItemSelected($N)",
                        LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME, ITEM_PARAM_NAME, ITEM_PARAM_NAME)
                .build();
    }

    private MethodSpec onPauseMethod() {
        return MethodSpec.methodBuilder("onPause")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$N.onPause($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME)
                .addStatement("super.onPause()")
                .build();
    }

    private MethodSpec onStopMethod() {
        return MethodSpec.methodBuilder("onStop")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$N.onStop($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME)
                .addStatement("super.onStop()")
                .build();
    }

    private MethodSpec onSaveInstanceStateMethod() {
        return MethodSpec.methodBuilder("onSaveInstanceState")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(bundleTypeName(), OUT_STATE_PARAM_NAME)
                .addStatement("super.onSaveInstanceState($N)", OUT_STATE_PARAM_NAME)
                .addStatement("$N.onSaveInstanceState($N(), $N)", LIGHT_CYCLE_DISPATCHER_FIELD_NAME,
                        OBJECT_METHOD_NAME, OUT_STATE_PARAM_NAME)
                .build();
    }

    private MethodSpec onDestroyViewMethod() {
        return MethodSpec.methodBuilder("onDestroyView")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$N.onDestroyView($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME)
                .addStatement("super.onDestroyView()")
                .build();
    }

    private MethodSpec onDestroyMethod() {
        return MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$N.onDestroy($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME)
                .addStatement("super.onDestroy()")
                .build();
    }

    private MethodSpec onDetachMethod() {
        return MethodSpec.methodBuilder("onDetach")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$N.onDetach($N())", LIGHT_CYCLE_DISPATCHER_FIELD_NAME, OBJECT_METHOD_NAME)
                .addStatement("super.onDetach()")
                .build();
    }

    private MethodSpec objectMethod() {
        return MethodSpec.methodBuilder(OBJECT_METHOD_NAME)
                .addAnnotation(suppressUncheckedWarningAnnotation())
                .addModifiers(Modifier.PRIVATE)
                .returns(objectTypeName())
                .addStatement("return ($T) this", objectTypeName())
                .build();
    }

    private MethodSpec bindIfNecessaryMethod() {
        return MethodSpec.methodBuilder(BIND_IF_NECESSARY_METHOD_NAME)
                .addModifiers(Modifier.PRIVATE)
                .addStatement("if (!$N) {", BOUND_FIELD_NAME)
                .addStatement("$T.bind(this)", lightCyclesTypeName())
                .addStatement("$N = true", BOUND_FIELD_NAME)
                .addStatement("}")
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

    private ClassName activityTypeName() {
        return ClassName.get("android.app", "Activity");
    }

    private ClassName bundleTypeName() {
        return ClassName.get("android.os", "Bundle");
    }

    private ClassName viewTypeName() {
        return ClassName.get("android.view", "View");
    }

    private ClassName menuItemTypeName() {
        return ClassName.get("android.view", "MenuItem");
    }

    private TypeName objectTypeName() {
        return TypeName.get(objectTypeMirror);
    }

    private TypeName fragmentTypeName() {
        return TypeName.get(fragmentDeclaredType);
    }

    private String javaFileResourceName() {
        return String.format("%s.%s", elementPackageName(), typeName());
    }

    private String typeName() {
        return String.format("%s%s", GENERATED_PREFIX, fragmentType().getSimpleName());
    }

    private String elementPackageName() {
        return fragmentType().getEnclosingElement().toString();
    }

    private TypeElement fragmentType() {
        return (TypeElement) fragmentDeclaredType.asElement();
    }
}
