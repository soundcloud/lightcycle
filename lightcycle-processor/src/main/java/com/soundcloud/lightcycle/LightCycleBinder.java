package com.soundcloud.lightcycle;

import com.squareup.javapoet.CodeBlock;

import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

abstract class LightCycleBinder {
    private static final String METHOD_BIND_NAME = "bind";
    private static final String METHOD_BIND_ARGUMENT_NAME = "target";
    private static final String METHOD_LIFT_NAME = LightCycleProcessor.LIB_PACKAGE + ".Lifts.lift";

    private static final CodeBlock EMPTY_BLOCK = CodeBlock.builder().build();

    static final LightCycleBinder DISPATCHER_NOT_FOUND = new LightCycleBinder() {
        @Override
        CodeBlock generateBind(Messager messager, Element element) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Could not find dispatcher type. Did you forget to add the generic type?", element);
            return EMPTY_BLOCK;
        }
    };

    static final LightCycleBinder EMPTY = new LightCycleBinder() {
        @Override
        CodeBlock generateBind(Messager messager, Element element) {
            return EMPTY_BLOCK;
        }
    };

    static LightCycleBinder forFields(final LightCycleDispatcherKind dispatcherKind, final DeclaredType type) {
        return new LightCycleBinder() {
            @Override
            CodeBlock generateBind(Messager messager, Element element) {
                final String liftedName = element.getSimpleName() + "$Lifted";

                final List<? extends TypeMirror> typeArguments = type.getTypeArguments();
                if (typeArguments.size() != 1) {
                    error(messager, typeArguments);
                    return EMPTY_BLOCK;
                } else {
                    return generateLiftAndBind(element, liftedName, typeArguments);
                }
            }

            private CodeBlock generateLiftAndBind(Element element, String liftedName, List<? extends TypeMirror> typeArguments) {
                final String lightCycleLiftedType = dispatcherKind.toTypeName(typeArguments.get(0).toString());
                return CodeBlock.builder()
                        .addStatement("final $N $N = $N($N.$N)",
                                lightCycleLiftedType,
                                liftedName,
                                METHOD_LIFT_NAME,
                                METHOD_BIND_ARGUMENT_NAME,
                                element.getSimpleName())
                        .addStatement("$N.$N($N)", METHOD_BIND_ARGUMENT_NAME, METHOD_BIND_NAME, liftedName)
                        .build();
            }

            private void error(Messager messager, List<? extends TypeMirror> typeArguments) {
                final String message = String.format("%s: Expected 1 type argument but found %d. TypeArguments:[%s]. Did you forget to add generic types?",
                        type.toString(),
                        typeArguments.size(),
                        typeArguments.toString());
                messager.printMessage(Diagnostic.Kind.ERROR, message, type.asElement());
            }
        };
    }

    static LightCycleBinder forParent(final String parentName) {
        return new LightCycleBinder() {
            @Override
            CodeBlock generateBind(Messager messager, Element element) {
                return CodeBlock.builder().addStatement("$N.$N($N)", parentName, METHOD_BIND_NAME, METHOD_BIND_ARGUMENT_NAME).build();
            }
        };
    }

    abstract CodeBlock generateBind(Messager messager, Element element);
}
