package com.soundcloud.lightcycle;

import com.soundcloud.lightcycle.util.Preconditions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

public abstract class LightCycleSupportFragment<HostType> extends Fragment implements LightCycleDispatcher<SupportFragmentLightCycle<HostType>> {

    private final SupportFragmentLightCycleDispatcher<HostType> lifeCycleDispatcher;
    private boolean bound;

    public LightCycleSupportFragment() {
        lifeCycleDispatcher = new SupportFragmentLightCycleDispatcher<>();
    }

    @Override
    public void bind(SupportFragmentLightCycle<HostType> lifeCycleComponent) {
        Preconditions.checkBindingTarget(lifeCycleComponent);
        lifeCycleDispatcher.bind(lifeCycleComponent);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bindIfNecessary();
        lifeCycleDispatcher.onAttach(host(), activity);
    }

    private void bindIfNecessary() {
        if (!bound) {
            LightCycles.bind(this);
            bound = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifeCycleDispatcher.onCreate(host(), savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifeCycleDispatcher.onViewCreated(host(), view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifeCycleDispatcher.onActivityCreated(host(), savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifeCycleDispatcher.onStart(host());
    }

    @Override
    public void onResume() {
        super.onResume();
        lifeCycleDispatcher.onResume(host());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return lifeCycleDispatcher.onOptionsItemSelected(host(), item);
    }

    @Override
    public void onPause() {
        lifeCycleDispatcher.onPause(host());
        super.onPause();
    }

    @Override
    public void onStop() {
        lifeCycleDispatcher.onStop(host());
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        lifeCycleDispatcher.onSaveInstanceState(host(), outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        lifeCycleDispatcher.onDestroyView(host());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifeCycleDispatcher.onDestroy(host());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifeCycleDispatcher.onDetach(host());
        super.onDetach();
    }

    @SuppressWarnings("unchecked")
    private HostType host() {
        return (HostType) this;
    }
}
