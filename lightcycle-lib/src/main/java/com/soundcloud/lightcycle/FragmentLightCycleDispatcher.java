package com.soundcloud.lightcycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;

import com.soundcloud.lightcycle.util.LightCycleBinderHelper;
import com.soundcloud.lightcycle.util.Preconditions;

import java.util.HashSet;
import java.util.Set;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentLightCycleDispatcher<HostType>
        implements LightCycleDispatcher<FragmentLightCycle<HostType>>, FragmentLightCycle<HostType> {

    private final Set<FragmentLightCycle<HostType>> fragmentLightCycles;
    private final LightCycleBinderHelper binderHelper;

    public FragmentLightCycleDispatcher() {
        this.fragmentLightCycles = new HashSet<>();
        this.binderHelper = new LightCycleBinderHelper(this);
    }

    @Override
    public void bind(FragmentLightCycle<HostType> lightCycle) {
        Preconditions.checkBindingTarget(lightCycle);
        fragmentLightCycles.add(lightCycle);
    }

    @Override
    public void onAttach(HostType host, Activity activity) {
        binderHelper.bindIfNecessary();
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onAttach(host, activity);
        }
    }

    @Override
    public void onAttach(HostType host, Context context) {
        binderHelper.bindIfNecessary();
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onAttach(host, context);
        }
    }

    @Override
    public void onCreate(HostType host, @Nullable Bundle bundle) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onCreate(host, bundle);
        }
    }

    @Override
    public void onViewCreated(HostType host, View view, @Nullable Bundle savedInstanceState) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onViewCreated(host, view, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(HostType host, Bundle bundle) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onActivityCreated(host, bundle);
        }
    }

    @Override
    public void onStart(HostType host) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onStart(host);
        }
    }

    @Override
    public void onResume(HostType host) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onResume(host);
        }
    }

    @Override
    public boolean onOptionsItemSelected(HostType host, MenuItem item) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            if (component.onOptionsItemSelected(host, item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause(HostType host) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onPause(host);
        }
    }

    @Override
    public void onStop(HostType host) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onStop(host);
        }
    }

    @Override
    public void onSaveInstanceState(HostType host, Bundle bundle) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onSaveInstanceState(host, bundle);
        }
    }

    @Override
    public void onDestroyView(HostType host) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onDestroyView(host);
        }
    }

    @Override
    public void onDestroy(HostType host) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onDestroy(host);
        }
    }

    @Override
    public void onDetach(HostType host) {
        for (FragmentLightCycle<HostType> component : fragmentLightCycles) {
            component.onDetach(host);
        }
    }
}
