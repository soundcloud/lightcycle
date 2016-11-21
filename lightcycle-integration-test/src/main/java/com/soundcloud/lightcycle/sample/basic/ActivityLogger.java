package com.soundcloud.lightcycle.sample.basic;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.soundcloud.lightcycle.DefaultActivityLightCycle;

import java.util.HashMap;
import java.util.Map;

class ActivityLogger extends DefaultActivityLightCycle<AppCompatActivity> {

    private Map<ActivityLifecycleCallback, Boolean> callbackCallState;

    enum ActivityLifecycleCallback {
        onCreate, onStart, onResume, onPause, onStop, onDestroy
    }

    ActivityLogger() {
        initializeCallbackCallStateMap();
    }

    @Override
    public void onCreate(AppCompatActivity activity, Bundle bundle) {
        callbackCallState.put(ActivityLifecycleCallback.onCreate, true);
    }

    @Override
    public void onStart(AppCompatActivity activity) {
        callbackCallState.put(ActivityLifecycleCallback.onStart, true);
    }

    @Override
    public void onResume(AppCompatActivity activity) {
        callbackCallState.put(ActivityLifecycleCallback.onResume, true);
    }

    @Override
    public void onPause(AppCompatActivity activity) {
        callbackCallState.put(ActivityLifecycleCallback.onPause, true);
    }

    @Override
    public void onStop(AppCompatActivity activity) {
        callbackCallState.put(ActivityLifecycleCallback.onStop, true);
    }

    @Override
    public void onDestroy(AppCompatActivity activity) {
        callbackCallState.put(ActivityLifecycleCallback.onDestroy, true);
    }

    boolean isActivityCallbackCalled(ActivityLifecycleCallback callback) {
        return callbackCallState.get(callback);
    }

    private void initializeCallbackCallStateMap() {
        this.callbackCallState = new HashMap<>();
        this.callbackCallState.put(ActivityLifecycleCallback.onCreate, false);
        this.callbackCallState.put(ActivityLifecycleCallback.onStart, false);
        this.callbackCallState.put(ActivityLifecycleCallback.onResume, false);
        this.callbackCallState.put(ActivityLifecycleCallback.onPause, false);
        this.callbackCallState.put(ActivityLifecycleCallback.onStop, false);
        this.callbackCallState.put(ActivityLifecycleCallback.onDestroy, false);
    }
}
