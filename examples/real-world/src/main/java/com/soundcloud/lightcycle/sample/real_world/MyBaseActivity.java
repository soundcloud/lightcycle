package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.ActivityLightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycleBinder;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.sample.real_world.tracker.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

// Implement you base activity to wire things up or use one of the provide default activities (like LightCycleAppCompatActivity).
public abstract class MyBaseActivity extends AppCompatActivity
        implements Screen, LightCycleDispatcher<ActivityLightCycle<MyBaseActivity>> {
    private final ActivityLightCycleDispatcher<MyBaseActivity> lightCycleDispatcher;

    public MyBaseActivity() {
        lightCycleDispatcher = new ActivityLightCycleDispatcher<>();
        SampleApp.getObjectGraph().inject(this);
    }

    @Override
    public void bind(ActivityLightCycle<MyBaseActivity> lightCycle) {
        lightCycleDispatcher.bind(lightCycle);
    }

    abstract void setActivityContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView();
        LightCycleBinder.bind(this);
        lightCycleDispatcher.onCreate(this, savedInstanceState);
    }

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
