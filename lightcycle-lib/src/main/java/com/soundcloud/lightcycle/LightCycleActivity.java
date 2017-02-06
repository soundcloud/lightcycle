package com.soundcloud.lightcycle;

import com.soundcloud.lightcycle.util.Preconditions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public abstract class LightCycleActivity<HostType>
        extends Activity
        implements LightCycleDispatcher<ActivityLightCycle<HostType>> {

    private final ActivityLightCycleDispatcher<HostType> lightCycleDispatcher;

    public LightCycleActivity() {
        lightCycleDispatcher = new ActivityLightCycleDispatcher<>();
    }

    @Override
    public void bind(ActivityLightCycle<HostType> lightCycle) {
        Preconditions.checkBindingTarget(lightCycle);
        lightCycleDispatcher.bind(lightCycle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView();
        LightCycles.bind(this);
        lightCycleDispatcher.onCreate(host(), savedInstanceState);
    }

    protected abstract void setActivityContentView();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        lightCycleDispatcher.onNewIntent(host(), intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lightCycleDispatcher.onStart(host());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return lightCycleDispatcher.onOptionsItemSelected(host(), item);
    }

    @Override
    protected void onStop() {
        lightCycleDispatcher.onStop(host());
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lightCycleDispatcher.onResume(host());
    }

    @Override
    protected void onPause() {
        lightCycleDispatcher.onPause(host());
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lightCycleDispatcher.onSaveInstanceState(host(), outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lightCycleDispatcher.onRestoreInstanceState(host(), savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        lightCycleDispatcher.onDestroy(host());
        super.onDestroy();
    }

    @SuppressWarnings("unchecked")
    private HostType host() {
        return (HostType) this;
    }
}
