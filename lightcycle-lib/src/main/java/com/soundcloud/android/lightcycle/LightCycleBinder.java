package com.soundcloud.android.lightcycle;

import android.util.Log;

import java.lang.reflect.Method;

public final class LightCycleBinder {
    private static final String TAG = LightCycleBinder.class.getSimpleName();
    private static final String ANDROID_PREFIX = "android.";
    private static final String JAVA_PREFIX = "java.";

    public static void bind(LightCycleDispatcher<?> target) {
        Method bindingMethod;
        try {
            bindingMethod = findViewBinderForClass(target.getClass());
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

    private static Method findViewBinderForClass(Class<?> cls) throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        Method lightCycleInjectionMethod;
        String clsName = cls.getName();
        if (clsName.startsWith(ANDROID_PREFIX) || clsName.startsWith(JAVA_PREFIX)) {
            Log.d(TAG, "MISS: Reached framework class. Abandoning search.");
            return null;
        }
        try {
            Class<?> binder = Class.forName(getInjectorClassName(clsName));
            lightCycleInjectionMethod = binder.getMethod("bind", cls);
            Log.d(TAG, "HIT: Loaded LightCycle binder class.");
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "Not found. Trying superclass " + cls.getSuperclass().getName());
            lightCycleInjectionMethod = findViewBinderForClass(cls.getSuperclass());
        }
        return lightCycleInjectionMethod;
    }
}
