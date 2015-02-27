package com.soundcloud.android.lightcycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

public class LightCycleSupportFragment extends Fragment {

    private final SupportFragmentLightCycleDispatcher lifeCycleDispatcher;

    public LightCycleSupportFragment() {
        lifeCycleDispatcher = new SupportFragmentLightCycleDispatcher();
    }

    public void addLifeCycleComponent(SupportFragmentLightCycle lifeCycleComponent) {
        lifeCycleDispatcher.attach(lifeCycleComponent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LightCycleInjector.attach(this);
        lifeCycleDispatcher.onCreate(this, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifeCycleDispatcher.onStart(this);
    }

    @Override
    public void onStop() {
        lifeCycleDispatcher.onStop(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        lifeCycleDispatcher.onResume(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return lifeCycleDispatcher.onOptionsItemSelected(this, item);
    }

    @Override
    public void onPause() {
        lifeCycleDispatcher.onPause(this);
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifeCycleDispatcher.onViewCreated(this, view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        lifeCycleDispatcher.onDestroyView(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifeCycleDispatcher.onDestroy(this);
        super.onDestroy();
    }
}
