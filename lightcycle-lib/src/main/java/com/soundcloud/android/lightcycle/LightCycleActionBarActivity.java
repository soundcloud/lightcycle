package com.soundcloud.android.lightcycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public abstract class LightCycleActionBarActivity extends ActionBarActivity
    implements LightCycleDispatcher<ActivityLightCycle<ActionBarActivity>> {

    private final ActivityLightCycleDispatcher<ActionBarActivity> lightCycleDispatcher;

    public LightCycleActionBarActivity() {
        lightCycleDispatcher = new ActivityLightCycleDispatcher<>();
    }

    @Override
    public void bind(ActivityLightCycle<ActionBarActivity> lightCycle) {
        lightCycleDispatcher.bind(lightCycle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView();
        LightCycleBinder.bind(this);
        lightCycleDispatcher.onCreate(this, savedInstanceState);
    }

    protected abstract void setActivityContentView();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        lightCycleDispatcher.onNewIntent(this, intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lightCycleDispatcher.onStart(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return lightCycleDispatcher.onOptionsItemSelected(this, item);
    }

    @Override
    protected void onStop() {
        lightCycleDispatcher.onStop(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lightCycleDispatcher.onResume(this);
    }

    @Override
    protected void onPause() {
        lightCycleDispatcher.onPause(this);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lightCycleDispatcher.onSaveInstanceState(this, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lightCycleDispatcher.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        lightCycleDispatcher.onDestroy(this);
        super.onDestroy();
    }
}
