package com.soundcloud.lightcycle;

import com.squareup.javawriter.JavaWriter;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;

abstract class LightCycleBinder {
    private static final String METHOD_BIND_NAME = "bind";
    private static final String METHOD_BIND_ARGUMENT_NAME = "target";
    private static final String METHOD_LIFT_NAME = LightCycleProcessor.LIB_PACKAGE + ".LightCycles.lift";

    static final LightCycleBinder DISPATCHER_NOT_FOUND = new LightCycleBinder() {
        @Override
        void emitBind(Messager messager, JavaWriter writer, Element element) throws IOException {
            messager.printMessage(Diagnostic.Kind.ERROR, "Could not find dispatcher type. Did you forget to add the generic type?", element);
        }
    };

    static final LightCycleBinder EMPTY = new LightCycleBinder() {
        @Override
        void emitBind(Messager messager, JavaWriter writer, Element element) throws IOException {
        }
    };

    static LightCycleBinder forFields(final LightCycleDispatcherKind dispatcherKind, final DeclaredType type) {
        return new LightCycleBinder() {
            @Override
            void emitBind(Messager messager, JavaWriter writer, Element element) throws IOException {
                final String liftedName = element.getSimpleName() + "$Lifted";

                final List<? extends TypeMirror> typeArguments = type.getTypeArguments();
                if (typeArguments.size() != 1) {
                    error(messager, typeArguments);
                } else {
                    emitLiftAndBind(writer, element, liftedName, typeArguments);
                }
            }

            private void emitLiftAndBind(JavaWriter writer, Element element, String liftedName, List<? extends TypeMirror> typeArguments) throws IOException {
                final String lightCycleLiftedType = dispatcherKind.toTypeName(typeArguments.get(0).toString());
                writer.emitStatement("final %s %s = %s(%s.%s)",
                        lightCycleLiftedType,
                        liftedName,
                        METHOD_LIFT_NAME,
                        METHOD_BIND_ARGUMENT_NAME,
                        element.getSimpleName());
                writer.emitStatement("%s.%s(%s)", METHOD_BIND_ARGUMENT_NAME, METHOD_BIND_NAME, liftedName);
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
            void emitBind(Messager messager, JavaWriter writer, Element element) throws IOException {
                writer.emitStatement("%s.%s(%s)", parentName, METHOD_BIND_NAME, METHOD_BIND_ARGUMENT_NAME);
            }
        };
    }

    abstract void emitBind(Messager messager, JavaWriter writer, Element element) throws IOException;
}
