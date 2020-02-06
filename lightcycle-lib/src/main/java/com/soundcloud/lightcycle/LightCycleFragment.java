package com.soundcloud.lightcycle;

import com.soundcloud.lightcycle.util.LightCycleBinderHelper;
import com.soundcloud.lightcycle.util.Preconditions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.Fragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class LightCycleFragment<HostType> extends Fragment implements LightCycleDispatcher<FragmentLightCycle<HostType>> {

    private final FragmentLightCycleDispatcher<HostType> lifeCycleDispatcher;
    private final LightCycleBinderHelper binderHelper;

    public LightCycleFragment() {
        this.lifeCycleDispatcher = new FragmentLightCycleDispatcher<>();
        this.binderHelper = new LightCycleBinderHelper(this);
    }

    @Override
    public void bind(FragmentLightCycle<HostType> lifeCycleComponent) {
        Preconditions.checkBindingTarget(lifeCycleComponent);
        lifeCycleDispatcher.bind(lifeCycleComponent);
    }

    @Override
    @TargetApi(23)
    public void onAttach(Context context) {
        super.onAttach(context);
        binderHelper.bindIfNecessary();
        lifeCycleDispatcher.onAttach(host(), context);
    }

    /*
     * Deprecated on API 23
     * Use onAttach(Context) instead
     */
    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        binderHelper.bindIfNecessary();
        lifeCycleDispatcher.onAttach(host(), activity);
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
