package com.soundcloud.lightcycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.soundcloud.lightcycle.util.Preconditions;

public abstract class LightCycleActivity<ActivityType extends LightCycleActivity>
        extends Activity
        implements LightCycleDispatcher<ActivityLightCycle<ActivityType>> {

    private final ActivityLightCycleDispatcher<ActivityType> lightCycleDispatcher;

    public LightCycleActivity() {
        lightCycleDispatcher = new ActivityLightCycleDispatcher<>();
    }

    @Override
    public void bind(ActivityLightCycle<ActivityType> lightCycle) {
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
    private ActivityType activity() {
        return (ActivityType) this;
    }
}
