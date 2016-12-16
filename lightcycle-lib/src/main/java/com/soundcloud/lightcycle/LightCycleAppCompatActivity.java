package com.soundcloud.lightcycle;

import com.soundcloud.lightcycle.util.Preconditions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public abstract class LightCycleAppCompatActivity<HostType>
        extends AppCompatActivity
        implements LightCycleDispatcher<ActivityLightCycle<HostType>> {

    private final ActivityLightCycleDispatcher<HostType> lightCycleDispatcher;

    public LightCycleAppCompatActivity() {
        this(new ActivityLightCycleDispatcher<HostType>());
    }

    public LightCycleAppCompatActivity(ActivityLightCycleDispatcher<HostType> dispatcher) {
        this.lightCycleDispatcher = dispatcher;
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
        lightCycleDispatcher.onCreate(activity(), savedInstanceState);
    }

    protected abstract void setActivityContentView();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        lightCycleDispatcher.onNewIntent(activity(), intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lightCycleDispatcher.onStart(activity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return lightCycleDispatcher.onOptionsItemSelected(activity(), item);
    }

    @Override
    protected void onStop() {
        lightCycleDispatcher.onStop(activity());
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lightCycleDispatcher.onResume(activity());
    }

    @Override
    protected void onPause() {
        lightCycleDispatcher.onPause(activity());
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lightCycleDispatcher.onSaveInstanceState(activity(), outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lightCycleDispatcher.onRestoreInstanceState(activity(), savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        lightCycleDispatcher.onDestroy(activity());
        super.onDestroy();
    }

    @SuppressWarnings("unchecked")
    private HostType activity() {
        return (HostType) this;
    }
}
