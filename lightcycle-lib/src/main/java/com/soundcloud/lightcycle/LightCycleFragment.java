package com.soundcloud.lightcycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.soundcloud.lightcycle.util.Preconditions;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class LightCycleFragment<FragmentType extends Fragment> extends Fragment implements LightCycleDispatcher<FragmentLightCycle<FragmentType>> {

    private final FragmentLightCycleDispatcher<FragmentType> lifeCycleDispatcher;
    private boolean bound;

    public LightCycleFragment() {
        lifeCycleDispatcher = new FragmentLightCycleDispatcher<>();
    }

    @Override
    public void bind(FragmentLightCycle<FragmentType> lifeCycleComponent) {
        Preconditions.checkBindingTarget(lifeCycleComponent);
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
        return lifeCycleDispatcher.onOptionsItemSelected(fragment(), item);
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
