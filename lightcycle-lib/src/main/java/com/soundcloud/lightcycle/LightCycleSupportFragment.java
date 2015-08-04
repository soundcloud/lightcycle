package com.soundcloud.lightcycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

public abstract class LightCycleSupportFragment extends Fragment implements LightCycleDispatcher<SupportFragmentLightCycle> {

    private final SupportFragmentLightCycleDispatcher<Fragment> lifeCycleDispatcher;
    private boolean bound;

    public LightCycleSupportFragment() {
        lifeCycleDispatcher = new SupportFragmentLightCycleDispatcher<>();
    }

    @Override
    public void bind(SupportFragmentLightCycle lifeCycleComponent) {
        lifeCycleDispatcher.bind(lifeCycleComponent);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bindIfNecessary();
        lifeCycleDispatcher.onAttach(this, activity);
    }

    private void bindIfNecessary() {
        if (!bound){
            LightCycleBinder.bind(this);
            bound = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onDetach() {
        lifeCycleDispatcher.onDetach(this);
        super.onDetach();
    }
}
