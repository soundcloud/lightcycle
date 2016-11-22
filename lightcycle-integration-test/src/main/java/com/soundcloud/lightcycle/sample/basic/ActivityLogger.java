package com.soundcloud.lightcycle.sample.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.sample.basic.callback.ActivityCallback;
import com.soundcloud.lightcycle.sample.basic.callback.ActivityLifecycleCallback;

import java.util.HashMap;
import java.util.Map;

class ActivityLogger extends DefaultActivityLightCycle<AppCompatActivity> {

    private Map<ActivityLifecycleCallback, Boolean> lifecycleCallbackCallState;
    private Map<ActivityCallback, Boolean> callbackCallState;

    ActivityLogger() {
        initializeLifecycleCallbackCallStateMap();
        initializeCallbackCallStateMap();
    }

    @Override
    public void onCreate(AppCompatActivity activity, Bundle bundle) {
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onCreate, true);
    }

    @Override
    public void onStart(AppCompatActivity activity) {
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onStart, true);
    }

    @Override
    public void onRestoreInstanceState(AppCompatActivity activity, Bundle bundle) {
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onRestoreInstanceState, true);
    }

    @Override
    public void onResume(AppCompatActivity activity) {
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onResume, true);
    }

    @Override
    public void onPause(AppCompatActivity activity) {
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onPause, true);
    }

    @Override
    public void onSaveInstanceState(AppCompatActivity activity, Bundle bundle) {
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onSaveInstanceState, true);
    }

    @Override
    public void onStop(AppCompatActivity activity) {
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onStop, true);
    }

    @Override
    public void onDestroy(AppCompatActivity activity) {
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onDestroy, true);
    }

    @Override
    public void onNewIntent(AppCompatActivity activity, Intent intent) {
        callbackCallState.put(ActivityCallback.onNewIntent, true);
    }

    @Override
    public boolean onOptionsItemSelected(AppCompatActivity activity, MenuItem item) {
        callbackCallState.put(ActivityCallback.onOptionsItemSelected, true);
        return super.onOptionsItemSelected(activity, item);
    }

    boolean isActivityLifecycleCallbackCalled(ActivityLifecycleCallback callback) {
        return lifecycleCallbackCallState.get(callback);
    }

    boolean isActivityCallbackCalled(ActivityCallback callback) {
        return callbackCallState.get(callback);
    }

    private void initializeLifecycleCallbackCallStateMap() {
        this.lifecycleCallbackCallState = new HashMap<>();
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onCreate, false);
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onStart, false);
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onRestoreInstanceState, false);
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onResume, false);
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onPause, false);
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onSaveInstanceState, false);
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onStop, false);
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onDestroy, false);
    }

    private void initializeCallbackCallStateMap() {
        this.callbackCallState = new HashMap<>();
        this.callbackCallState.put(ActivityCallback.onNewIntent, false);
        this.callbackCallState.put(ActivityCallback.onOptionsItemSelected, false);
    }
}
