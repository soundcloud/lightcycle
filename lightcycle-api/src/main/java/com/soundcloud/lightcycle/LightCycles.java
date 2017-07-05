package com.soundcloud.lightcycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Method;

public final class LightCycles {
    private static final String TAG = LightCycles.class.getSimpleName();
    private static final String ANDROID_PREFIX = "android.";
    private static final String JAVA_PREFIX = "java.";
    private static boolean loggingEnabled = Log.isLoggable(TAG, Log.DEBUG);

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
            log("MISS: Reached framework class. Abandoning search.");
            return null;
        }
        try {
            Class<?> binder = Class.forName(getInjectorClassName(clsName));
            lightCycleInjectionMethod = binder.getMethod("bind", cls);
            log("HIT: Loaded LightCycle binder class.");
        } catch (ClassNotFoundException e) {
            log("Not found. Trying superclass " + cls.getSuperclass().getName());
            lightCycleInjectionMethod = findBinderForClass(cls.getSuperclass());
        }
        return lightCycleInjectionMethod;
    }

    private static void log(String msg) {
        if (loggingEnabled) {
            Log.d(TAG, msg);
        }
    }

    public static <Source, Target extends Source> ActivityLightCycle<Target> lift(final ActivityLightCycle<Source> lightCycle) {
        return new LiftedActivityLightCycle<>(lightCycle);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static <Source, Target extends Source> FragmentLightCycle<Target> lift(final FragmentLightCycle<Source> lightCycle) {
        return new LiftedFragmentLightCycle<>(lightCycle);
    }


    public static <Source, Target extends Source> SupportFragmentLightCycle<Target> lift(final SupportFragmentLightCycle<Source> lightCycle) {
        return new LiftedSupportFragmentLightCycle<>(lightCycle);
    }

    static final class LiftedActivityLightCycle<Source, Target extends Source> implements ActivityLightCycle<Target> {

        private final ActivityLightCycle<Source> lightCycle;

        LiftedActivityLightCycle(ActivityLightCycle<Source> lightCycle) {
            this.lightCycle = lightCycle;
        }

        @Override
        public void onCreate(Target activity, Bundle bundle) {
            lightCycle.onCreate(activity, bundle);
        }

        @Override
        public void onNewIntent(Target activity, Intent intent) {
            lightCycle.onNewIntent(activity, intent);
        }

        @Override
        public void onStart(Target activity) {
            lightCycle.onStart(activity);
        }

        @Override
        public void onResume(Target activity) {
            lightCycle.onResume(activity);
        }

        @Override
        public boolean onOptionsItemSelected(Target activity, MenuItem item) {
            return lightCycle.onOptionsItemSelected(activity, item);
        }

        @Override
        public void onPause(Target activity) {
            lightCycle.onPause(activity);
        }

        @Override
        public void onStop(Target activity) {
            lightCycle.onStop(activity);
        }

        @Override
        public void onSaveInstanceState(Target activity, Bundle bundle) {
            lightCycle.onSaveInstanceState(activity, bundle);
        }

        @Override
        public void onRestoreInstanceState(Target activity, Bundle bundle) {
            lightCycle.onRestoreInstanceState(activity, bundle);
        }

        @Override
        public void onDestroy(Target activity) {
            lightCycle.onDestroy(activity);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            LiftedActivityLightCycle<?, ?> that = (LiftedActivityLightCycle<?, ?>) o;

            return lightCycle != null ? lightCycle.equals(that.lightCycle) : that.lightCycle == null;
        }

        @Override
        public int hashCode() {
            return lightCycle != null ? lightCycle.hashCode() : 0;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    static final class LiftedFragmentLightCycle<Source, Target extends Source> implements FragmentLightCycle<Target> {
        private final FragmentLightCycle<Source> lightCycle;

        LiftedFragmentLightCycle(FragmentLightCycle<Source> lightCycle) {
            this.lightCycle = lightCycle;
        }

        @Override
        public void onAttach(Target fragment, Activity activity) {
            lightCycle.onAttach(fragment, activity);
        }

        @Override
        public void onAttach(Target fragment, Context context) {
            lightCycle.onAttach(fragment, context);
        }

        @Override
        public void onCreate(Target fragment, Bundle bundle) {
            lightCycle.onCreate(fragment, bundle);
        }

        @Override
        public void onViewCreated(Target fragment, View view, Bundle savedInstanceState) {
            lightCycle.onViewCreated(fragment, view, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Target fragment, Bundle bundle) {
            lightCycle.onActivityCreated(fragment, bundle);
        }

        @Override
        public void onStart(Target fragment) {
            lightCycle.onStart(fragment);
        }

        @Override
        public void onResume(Target fragment) {
            lightCycle.onResume(fragment);
        }

        @Override
        public boolean onOptionsItemSelected(Target fragment, MenuItem item) {
            return lightCycle.onOptionsItemSelected(fragment, item);
        }

        @Override
        public void onPause(Target fragment) {
            lightCycle.onPause(fragment);
        }

        @Override
        public void onStop(Target fragment) {
            lightCycle.onStop(fragment);
        }

        @Override
        public void onSaveInstanceState(Target fragment, Bundle bundle) {
            lightCycle.onSaveInstanceState(fragment, bundle);
        }

        @Override
        public void onDestroyView(Target fragment) {
            lightCycle.onDestroyView(fragment);
        }

        @Override
        public void onDestroy(Target fragment) {
            lightCycle.onDestroy(fragment);
        }

        @Override
        public void onDetach(Target fragment) {
            lightCycle.onDetach(fragment);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            LiftedFragmentLightCycle<?, ?> that = (LiftedFragmentLightCycle<?, ?>) o;

            return lightCycle != null ? lightCycle.equals(that.lightCycle) : that.lightCycle == null;

        }

        @Override
        public int hashCode() {
            return lightCycle != null ? lightCycle.hashCode() : 0;
        }
    }

    static final class LiftedSupportFragmentLightCycle<Source, Target extends Source> implements SupportFragmentLightCycle<Target> {

        private final SupportFragmentLightCycle<Source> lightCycle;

        LiftedSupportFragmentLightCycle(SupportFragmentLightCycle<Source> lightCycle) {
            this.lightCycle = lightCycle;
        }

        @Override
        public void onAttach(Target fragment, Activity activity) {
            lightCycle.onAttach(fragment, activity);
        }

        @Override
        public void onCreate(Target fragment, Bundle bundle) {
            lightCycle.onCreate(fragment, bundle);
        }

        @Override
        public void onViewCreated(Target fragment, View view, Bundle savedInstanceState) {
            lightCycle.onViewCreated(fragment, view, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Target fragment, Bundle bundle) {
            lightCycle.onActivityCreated(fragment, bundle);
        }

        @Override
        public void onStart(Target fragment) {
            lightCycle.onStart(fragment);
        }

        @Override
        public void onResume(Target fragment) {
            lightCycle.onResume(fragment);
        }

        @Override
        public boolean onOptionsItemSelected(Target fragment, MenuItem item) {
            return lightCycle.onOptionsItemSelected(fragment, item);
        }

        @Override
        public void onPause(Target fragment) {
            lightCycle.onPause(fragment);
        }

        @Override
        public void onStop(Target fragment) {
            lightCycle.onStop(fragment);
        }

        @Override
        public void onSaveInstanceState(Target fragment, Bundle bundle) {
            lightCycle.onSaveInstanceState(fragment, bundle);
        }

        @Override
        public void onDestroyView(Target fragment) {
            lightCycle.onDestroyView(fragment);
        }

        @Override
        public void onDestroy(Target fragment) {
            lightCycle.onDestroy(fragment);
        }

        @Override
        public void onDetach(Target fragment) {
            lightCycle.onDetach(fragment);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            LiftedSupportFragmentLightCycle<?, ?> that = (LiftedSupportFragmentLightCycle<?, ?>) o;

            return lightCycle != null ? lightCycle.equals(that.lightCycle) : that.lightCycle == null;

        }

        @Override
        public int hashCode() {
            return lightCycle != null ? lightCycle.hashCode() : 0;
        }
    }
}
