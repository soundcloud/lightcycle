package com.soundcloud.lightcycle.sample.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.soundcloud.lightcycle.sample.basic.callback.ActivityCallback;
import com.soundcloud.lightcycle.sample.basic.callback.ActivityLifecycleCallback;

class AppCompatActivityLogger extends BaseActivityLogger<AppCompatActivity> {

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
}
