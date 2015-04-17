package com.soundcloud.android.lightcycle;

public abstract class LightCycleInjector<TargetT> {

    public static void attach(LightCycleDispatcher<?> target) {
        attach(target, getInjectorClassName(target));
    }

    public static void attach(LightCycleDispatcher<?> target, String injectorClassName) {
        LightCycleInjector injector;
        try {
            final Class<?> injectorClass = target.getClass().getClassLoader().loadClass(injectorClassName);
            injector = (LightCycleInjector) injectorClass.newInstance();
            injector.inject(target);
        } catch (Exception e) {
            // no injectors found, so ignore.
        }
    }

    private static String getInjectorClassName(Object target) {
        return target.getClass().getCanonicalName() + "$LightCycleInjector";
    }

    public abstract void inject(TargetT target);
}
