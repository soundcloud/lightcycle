package com.soundcloud.lightcycle.integration_test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.soundcloud.lightcycle.DefaultFragmentLightCycle;
import com.soundcloud.lightcycle.integration_test.callback.FragmentLifecycleCallback;

import java.util.HashMap;
import java.util.Map;

public class FragmentLogger extends DefaultFragmentLightCycle<SampleFragment> {

    private Map<FragmentLifecycleCallback, Boolean> lifecycleCallbackCallState;

    FragmentLogger() {
        initializeLifecycleCallbackCallStateMap();
    }

    @Override
    public void onAttach(SampleFragment fragment, Activity activity) {
        super.onAttach(fragment, activity);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onAttach, true);
    }

    @Override
    public void onAttach(SampleFragment fragment, Context context) {
        super.onAttach(fragment, context);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onAttach, true);
    }

    @Override
    public void onCreate(SampleFragment fragment, Bundle bundle) {
        super.onCreate(fragment, bundle);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onCreate, true);
    }

    @Override
    public void onViewCreated(SampleFragment fragment, View view, Bundle savedInstanceState) {
        super.onViewCreated(fragment, view, savedInstanceState);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onViewCreated, true);
    }

    @Override
    public void onActivityCreated(SampleFragment fragment, Bundle bundle) {
        super.onActivityCreated(fragment, bundle);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onActivityCreated, true);
    }

    @Override
    public void onStart(SampleFragment fragment) {
        super.onStart(fragment);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onStart, true);
    }

    @Override
    public void onResume(SampleFragment fragment) {
        super.onResume(fragment);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onResume, true);
    }

    @Override
    public void onPause(SampleFragment fragment) {
        super.onPause(fragment);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onPause, true);
    }

    @Override
    public void onStop(SampleFragment fragment) {
        super.onStop(fragment);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onStop, true);
    }

    @Override
    public void onSaveInstanceState(SampleFragment fragment, Bundle bundle) {
        super.onSaveInstanceState(fragment, bundle);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onSaveInstanceState, true);
    }

    @Override
    public void onDestroyView(SampleFragment fragment) {
        super.onDestroyView(fragment);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onDestroyView, true);
    }

    @Override
    public void onDestroy(SampleFragment fragment) {
        super.onDestroy(fragment);
        lifecycleCallbackCallState.put(FragmentLifecycleCallback.onDestroy, true);
    }

    @Override
    public void onDetach(SampleFragment fragment) {
        super.onDetach(fragment);
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
