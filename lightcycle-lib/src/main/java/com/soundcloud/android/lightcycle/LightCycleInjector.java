package com.soundcloud.android.lightcycle;

public abstract class LightCycleInjector {

    public static void attach(LightCycleSupportFragment fragment) {
        LightCycleInjector injector;
        final String injectorClassName = getInjectorClassName(fragment);
        try {
            final Class<?> injectorClass = fragment.getClass().getClassLoader().loadClass(injectorClassName);
            injector = (LightCycleInjector) injectorClass.newInstance();
            injector.inject(fragment);
        } catch (Exception e) {
            // no injectors found, so ignore.
        }
    }

    private static String getInjectorClassName(Object fragment) {
        return fragment.getClass().getCanonicalName() + "$LightCycleInjector";
    }

    public abstract void inject(LightCycleSupportFragment fragment);
}
