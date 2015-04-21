package com.soundcloud.android.lightcycle;

import org.jetbrains.annotations.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.HashSet;
import java.util.Set;

public class ActivityLightCycleDispatcher<T extends Activity>
        implements LightCycleDispatcher<ActivityLightCycle<T>>, ActivityLightCycle<T> {
    private final Set<ActivityLightCycle<T>> activityLightCycles;

    public ActivityLightCycleDispatcher() {
        this.activityLightCycles = new HashSet<>();
    }


    @Override
    public void bind(ActivityLightCycle<T> lightCycle) {
        this.activityLightCycles.add(lightCycle);
    }

    @Override
    public void onCreate(T activity, @Nullable Bundle bundle) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            component.onCreate(activity, bundle);
        }
    }

    @Override
    public void onNewIntent(T activity, Intent intent) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            component.onNewIntent(activity, intent);
        }
    }

    @Override
    public void onStart(T activity) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            component.onStart(activity);
        }
    }

    @Override
    public void onResume(T activity) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            component.onResume(activity);
        }
    }

    @Override
    public boolean onOptionsItemSelected(T activity, MenuItem item) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            if (component.onOptionsItemSelected(activity, item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause(T activity) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            component.onPause(activity);
        }
    }

    @Override
    public void onStop(T activity) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            component.onStop(activity);
        }
    }

    @Override
    public void onSaveInstanceState(T activity, Bundle bundle) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            component.onSaveInstanceState(activity, bundle);
        }
    }

    @Override
    public void onRestoreInstanceState(T activity, Bundle bundle) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            component.onRestoreInstanceState(activity, bundle);
        }
    }

    @Override
    public void onDestroy(T activity) {
        for (ActivityLightCycle<T> component : activityLightCycles) {
            component.onDestroy(activity);
        }
    }
}
