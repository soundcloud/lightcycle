package com.soundcloud.android.lightcycle;

import org.jetbrains.annotations.Nullable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

public class SupportFragmentLightCycleDispatcher<T extends Fragment>
        implements LightCycleDispatcher<SupportFragmentLightCycle<T>>, SupportFragmentLightCycle<T> {
    private final Set<SupportFragmentLightCycle<T>> fragmentLightCycles;

    public SupportFragmentLightCycleDispatcher() {
        this.fragmentLightCycles = new HashSet<>();
    }

    @Override
    public void bind(SupportFragmentLightCycle<T> lightCycle) {
        fragmentLightCycles.add(lightCycle);
    }

    @Override
    public void onCreate(T fragment, @Nullable Bundle bundle) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onCreate(fragment, bundle);
        }
    }

    @Override
    public void onViewCreated(T fragment, View view, @Nullable Bundle savedInstanceState) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onViewCreated(fragment, view, savedInstanceState);
        }
    }

    @Override
    public void onStart(T fragment) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onStart(fragment);
        }
    }

    @Override
    public void onResume(T fragment) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onResume(fragment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(T fragment, MenuItem item) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            if (component.onOptionsItemSelected(fragment, item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause(T fragment) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onPause(fragment);
        }
    }

    @Override
    public void onStop(T fragment) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onStop(fragment);
        }
    }

    @Override
    public void onSaveInstanceState(T fragment, Bundle bundle) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onSaveInstanceState(fragment, bundle);
        }
    }

    @Override
    public void onRestoreInstanceState(T fragment, Bundle bundle) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onRestoreInstanceState(fragment, bundle);
        }
    }

    @Override
    public void onDestroyView(T fragment) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onDestroyView(fragment);
        }
    }

    @Override
    public void onDestroy(T fragment) {
        for (SupportFragmentLightCycle<T> component : fragmentLightCycles) {
            component.onDestroy(fragment);
        }
    }
}
