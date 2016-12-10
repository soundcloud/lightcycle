package com.soundcloud.lightcycle;

import com.soundcloud.lightcycle.util.Preconditions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentLightCycleDispatcher<T extends Fragment>
        implements LightCycleDispatcher<FragmentLightCycle<T>>, FragmentLightCycle<T> {
    private final Set<FragmentLightCycle<T>> fragmentLightCycles;

    public FragmentLightCycleDispatcher() {
        this.fragmentLightCycles = new HashSet<>();
    }

    @Override
    public void bind(FragmentLightCycle<T> lightCycle) {
        Preconditions.checkBindingTarget(lightCycle);
        fragmentLightCycles.add(lightCycle);
    }

    @Override
    public void onAttach(T fragment, Activity activity) {
        LightCycles.bind(this);
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onAttach(fragment, activity);
        }
    }

    @Override
    public void onAttach(T fragment, Context context) {
        LightCycles.bind(this);
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onAttach(fragment, context);
        }
    }

    @Override
    public void onCreate(T fragment, @Nullable Bundle bundle) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onCreate(fragment, bundle);
        }
    }

    @Override
    public void onViewCreated(T fragment, View view, @Nullable Bundle savedInstanceState) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onViewCreated(fragment, view, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(T fragment, Bundle bundle) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onActivityCreated(fragment, bundle);
        }
    }

    @Override
    public void onStart(T fragment) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onStart(fragment);
        }
    }

    @Override
    public void onResume(T fragment) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onResume(fragment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(T fragment, MenuItem item) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            if (component.onOptionsItemSelected(fragment, item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause(T fragment) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onPause(fragment);
        }
    }

    @Override
    public void onStop(T fragment) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onStop(fragment);
        }
    }

    @Override
    public void onSaveInstanceState(T fragment, Bundle bundle) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onSaveInstanceState(fragment, bundle);
        }
    }

    @Override
    public void onDestroyView(T fragment) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onDestroyView(fragment);
        }
    }

    @Override
    public void onDestroy(T fragment) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onDestroy(fragment);
        }
    }

    @Override
    public void onDetach(T fragment) {
        for (FragmentLightCycle<T> component : fragmentLightCycles) {
            component.onDetach(fragment);
        }
    }
}
