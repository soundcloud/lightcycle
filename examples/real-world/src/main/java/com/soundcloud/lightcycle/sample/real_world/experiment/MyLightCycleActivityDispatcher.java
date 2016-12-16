package com.soundcloud.lightcycle.sample.real_world.experiment;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.HashSet;
import java.util.Set;

public class MyLightCycleActivityDispatcher<HostType>
        implements LightCycleDispatcher<MyLightCycle<HostType>>, ActivityLightCycle<HostType> {

    private final Set<MyLightCycle<HostType>> lightCycles;

    public MyLightCycleActivityDispatcher() {
        this.lightCycles = new HashSet<>();
    }

    @Override
    public void bind(MyLightCycle<HostType> lightCycle) {
        lightCycles.add(lightCycle);
    }

    @Override
    public void onCreate(HostType host, Bundle bundle) {

    }

    @Override
    public void onNewIntent(HostType host, Intent intent) {

    }

    @Override
    public void onStart(HostType host) {

    }

    @Override
    public void onResume(HostType host) {
        for (MyLightCycle<HostType> component : lightCycles) {
            component.onScreen(host);
        }
    }

    @Override
    public boolean onOptionsItemSelected(HostType host, MenuItem item) {
        return false;
    }

    @Override
    public void onPause(HostType host) {
        for (MyLightCycle<HostType> component : lightCycles) {
            component.offScreen(host);
        }
    }

    @Override
    public void onStop(HostType host) {

    }

    @Override
    public void onSaveInstanceState(HostType host, Bundle bundle) {

    }

    @Override
    public void onRestoreInstanceState(HostType host, Bundle bundle) {

    }

    @Override
    public void onDestroy(HostType host) {

    }
}
