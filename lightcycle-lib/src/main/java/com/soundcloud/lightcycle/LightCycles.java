package com.soundcloud.lightcycle;

import java.lang.reflect.Method;

public final class LightCycles {
    private static final String TAG = LightCycles.class.getSimpleName();
    private static final String ANDROID_PREFIX = "android.";
    private static final String JAVA_PREFIX = "java.";

    @SuppressWarnings("PMD.EmptyCatchBlock")
    public static void bind(LightCycleDispatcher<?> target) {
        Method bindingMethod;
        try {
            bindingMethod = findBinderForClass(target.getClass());
            if (bindingMethod != null) {
                bindingMethod.invoke(null, target);
            }
        } catch (Exception e) {
            // no binder found, so ignore.
        }
    }

    private static String getInjectorClassName(String clsName) {
        return clsName + "$LightCycleBinder";
    }

    private static Method findBinderForClass(Class<?> cls)
            throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        Method lightCycleInjectionMethod;
        String clsName = cls.getName();
        if (clsName.startsWith(ANDROID_PREFIX) || clsName.startsWith(JAVA_PREFIX)) {
            return null;
        }
        try {
            Class<?> binder = Class.forName(getInjectorClassName(clsName));
            lightCycleInjectionMethod = binder.getMethod("bind", cls);
        } catch (ClassNotFoundException e) {
            lightCycleInjectionMethod = findBinderForClass(cls.getSuperclass());
        }
        return lightCycleInjectionMethod;
    }
}
