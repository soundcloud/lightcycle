package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycles;
import com.soundcloud.lightcycle.sample.real_world.experiment.MyLightCycle;
import com.soundcloud.lightcycle.sample.real_world.experiment.MyLightCycleActivityDispatcher;
import com.soundcloud.lightcycle.sample.real_world.home.HomeView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

// TODO: 12/16/16 <HostType> (processor cannot handle it now)
public abstract class MyLightCycleAppCompatActivity
        extends AppCompatActivity
        implements LightCycleDispatcher<MyLightCycle<HomeView>> {

    private final MyLightCycleActivityDispatcher<HomeView> dispatcher;

    public MyLightCycleAppCompatActivity() {
        dispatcher = new MyLightCycleActivityDispatcher<>();
    }

    @Override
    public void bind(MyLightCycle<HomeView> lightCycle) {
        // TODO: 12/16/16
        // Preconditions.checkBindingTarget(lightCycle);
        dispatcher.bind(lightCycle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView();
        LightCycles.bind(this);
        dispatcher.onCreate(activity(), savedInstanceState);
    }

    protected abstract void setActivityContentView();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dispatcher.onNewIntent(activity(), intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dispatcher.onStart(activity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return dispatcher.onOptionsItemSelected(activity(), item);
    }

    @Override
    protected void onStop() {
        dispatcher.onStop(activity());
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dispatcher.onResume(activity());
    }

    @Override
    protected void onPause() {
        dispatcher.onPause(activity());
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        dispatcher.onSaveInstanceState(activity(), outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dispatcher.onRestoreInstanceState(activity(), savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        dispatcher.onDestroy(activity());
        super.onDestroy();
    }

    @SuppressWarnings("unchecked")
    private HomeView activity() {
        return (HomeView) this;
    }
}
