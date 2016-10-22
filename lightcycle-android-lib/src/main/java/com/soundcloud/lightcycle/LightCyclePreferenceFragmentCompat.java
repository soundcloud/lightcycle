package com.soundcloud.lightcycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.MenuItem;
import android.view.View;

public abstract class LightCyclePreferenceFragmentCompat<FragmentType extends Fragment>
        extends PreferenceFragmentCompat implements LightCycleDispatcher<SupportFragmentLightCycle<FragmentType>> {

    private final SupportFragmentLightCycleDispatcher<FragmentType> lifeCycleDispatcher;
    private boolean bound;

    public LightCyclePreferenceFragmentCompat() {
        lifeCycleDispatcher = new SupportFragmentLightCycleDispatcher<>();
    }

    @Override
    public void bind(SupportFragmentLightCycle<FragmentType> lifeCycleComponent) {
        lifeCycleDispatcher.bind(lifeCycleComponent);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bindIfNecessary();
        lifeCycleDispatcher.onAttach(fragment(), activity);
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
        lifeCycleDispatcher.onCreate(fragment(), savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifeCycleDispatcher.onViewCreated(fragment(), view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifeCycleDispatcher.onActivityCreated(fragment(), savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifeCycleDispatcher.onStart(fragment());
    }

    @Override
    public void onResume() {
        super.onResume();
        lifeCycleDispatcher.onResume(fragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return lifeCycleDispatcher.onOptionsItemSelected(fragment(), item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        lifeCycleDispatcher.onPause(fragment());
        super.onPause();
    }

    @Override
    public void onStop() {
        lifeCycleDispatcher.onStop(fragment());
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        lifeCycleDispatcher.onSaveInstanceState(fragment(), outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        lifeCycleDispatcher.onDestroyView(fragment());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifeCycleDispatcher.onDestroy(fragment());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifeCycleDispatcher.onDetach(fragment());
        super.onDetach();
    }

    @SuppressWarnings("unchecked")
    private FragmentType fragment() {
        return (FragmentType) this;
    }
}
