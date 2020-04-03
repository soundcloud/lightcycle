package com.soundcloud.lightcycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.soundcloud.lightcycle.util.Preconditions;

import java.util.HashSet;
import java.util.Set;

public class ActivityLightCycleDispatcher<HostType>
        implements LightCycleDispatcher<ActivityLightCycle<HostType>>, ActivityLightCycle<HostType> {
    private final Set<ActivityLightCycle<HostType>> activityLightCycles;

    public ActivityLightCycleDispatcher() {
        this.activityLightCycles = new HashSet<>();
    }


    @Override
    public void bind(ActivityLightCycle<HostType> lightCycle) {
        Preconditions.checkBindingTarget(lightCycle);
        this.activityLightCycles.add(lightCycle);
    }

    @Override
    public void onCreate(HostType host, @Nullable Bundle bundle) {
        LightCycles.bind(this);
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            component.onCreate(host, bundle);
        }
    }

    @Override
    public void onNewIntent(HostType host, Intent intent) {
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            component.onNewIntent(host, intent);
        }
    }

    @Override
    public void onStart(HostType host) {
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            component.onStart(host);
        }
    }

    @Override
    public void onResume(HostType host) {
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            component.onResume(host);
        }
    }

    @Override
    public boolean onOptionsItemSelected(HostType host, MenuItem item) {
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            if (component.onOptionsItemSelected(host, item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause(HostType host) {
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            component.onPause(host);
        }
    }

    @Override
    public void onStop(HostType host) {
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            component.onStop(host);
        }
    }

    @Override
    public void onSaveInstanceState(HostType host, Bundle bundle) {
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            component.onSaveInstanceState(host, bundle);
        }
    }

    @Override
    public void onRestoreInstanceState(HostType host, Bundle bundle) {
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            component.onRestoreInstanceState(host, bundle);
        }
    }

    @Override
    public void onDestroy(HostType host) {
        for (ActivityLightCycle<HostType> component : activityLightCycles) {
            component.onDestroy(host);
        }
    }
}
