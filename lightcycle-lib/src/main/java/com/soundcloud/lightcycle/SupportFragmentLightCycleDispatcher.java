package com.soundcloud.lightcycle;

import com.soundcloud.lightcycle.util.Preconditions;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

public class SupportFragmentLightCycleDispatcher<HostType>
        implements LightCycleDispatcher<SupportFragmentLightCycle<HostType>>, SupportFragmentLightCycle<HostType> {

    private final Set<SupportFragmentLightCycle<HostType>> fragmentLightCycles;

    public SupportFragmentLightCycleDispatcher() {
        this.fragmentLightCycles = new HashSet<>();
    }

    @Override
    public void bind(SupportFragmentLightCycle<HostType> lightCycle) {
        Preconditions.checkBindingTarget(lightCycle);
        fragmentLightCycles.add(lightCycle);
    }

    @Override
    public void onAttach(HostType host, Activity activity) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onAttach(host, activity);
        }
    }

    @Override
    public void onCreate(HostType host, @Nullable Bundle bundle) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onCreate(host, bundle);
        }
    }

    @Override
    public void onViewCreated(HostType host, View view, @Nullable Bundle savedInstanceState) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onViewCreated(host, view, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(HostType host, Bundle bundle) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onActivityCreated(host, bundle);
        }
    }

    @Override
    public void onStart(HostType host) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onStart(host);
        }
    }

    @Override
    public void onResume(HostType host) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onResume(host);
        }
    }

    @Override
    public boolean onOptionsItemSelected(HostType host, MenuItem item) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            if (component.onOptionsItemSelected(host, item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause(HostType host) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onPause(host);
        }
    }

    @Override
    public void onStop(HostType host) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onStop(host);
        }
    }

    @Override
    public void onSaveInstanceState(HostType host, Bundle bundle) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onSaveInstanceState(host, bundle);
        }
    }

    @Override
    public void onDestroyView(HostType host) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onDestroyView(host);
        }
    }

    @Override
    public void onDestroy(HostType host) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onDestroy(host);
        }
    }

    @Override
    public void onDetach(HostType host) {
        for (SupportFragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onDetach(host);
        }
    }
}
