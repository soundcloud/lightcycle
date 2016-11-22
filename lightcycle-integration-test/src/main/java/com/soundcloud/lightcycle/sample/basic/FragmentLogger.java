package com.soundcloud.lightcycle.sample.basic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.soundcloud.lightcycle.DefaultFragmentLightCycle;
import com.soundcloud.lightcycle.sample.basic.callback.FragmentLifecycleCallback;

import java.util.HashMap;
import java.util.Map;

public class FragmentLogger extends DefaultFragmentLightCycle<SampleFragment> {

    private Map<FragmentLifecycleCallback, Boolean> lifecycleCallbackCallState;

    FragmentLogger() {
        initializeLifecycleCallbackCallStateMap();
    }

    @Override
    public void onAttach(SampleFragment fragment, Activity activity) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onAttach, true);
    }

    @Override
    public void onAttach(SampleFragment fragment, Context context) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onAttach, true);
    }

    @Override
    public void onCreate(SampleFragment fragment, Bundle bundle) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onCreate, true);
    }

    @Override
    public void onViewCreated(SampleFragment fragment, View view, Bundle savedInstanceState) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onViewCreated, true);
    }

    @Override
    public void onActivityCreated(SampleFragment fragment, Bundle bundle) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onActivityCreated, true);
    }

    @Override
    public void onStart(SampleFragment fragment) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onStart, true);
    }

    @Override
    public void onResume(SampleFragment fragment) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onResume, true);
    }

    @Override
    public void onPause(SampleFragment fragment) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onPause, true);
    }

    @Override
    public void onStop(SampleFragment fragment) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onStop, true);
    }

    @Override
    public void onSaveInstanceState(SampleFragment fragment, Bundle bundle) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onSaveInstanceState, true);
    }

    @Override
    public void onDestroyView(SampleFragment fragment) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onDestroyView, true);
    }

    @Override
    public void onDestroy(SampleFragment fragment) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onDestroy, true);
    }

    @Override
    public void onDetach(SampleFragment fragment) {
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onDetach, true);
    }

    boolean isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback callback) {
        return lifecycleCallbackCallState.get(callback);
    }

    private void initializeLifecycleCallbackCallStateMap() {
        this.lifecycleCallbackCallState = new HashMap<>();
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onAttach, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onCreate, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onViewCreated, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onActivityCreated, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onStart, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onResume, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onPause, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onStop, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onSaveInstanceState, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onDestroyView, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onDestroy, false);
        this.lifecycleCallbackCallState.put(FragmentLifecycleCallback.onDetach, false);
    }
}
