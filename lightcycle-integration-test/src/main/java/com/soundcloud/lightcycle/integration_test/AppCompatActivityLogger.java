package com.soundcloud.lightcycle.integration_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.soundcloud.lightcycle.integration_test.callback.ActivityLifecycleCallback;

class AppCompatActivityLogger extends BaseActivityLogger<AppCompatActivity> {

    @Override
    public void onCreate(AppCompatActivity activity, Bundle bundle) {
        super.onCreate(activity, bundle);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onCreate, true);
    }

    @Override
    public void onStart(AppCompatActivity activity) {
        super.onStart(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onStart, true);
    }

    @Override
    public void onRestoreInstanceState(AppCompatActivity activity, Bundle bundle) {
        super.onRestoreInstanceState(activity, bundle);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onRestoreInstanceState, true);
    }

    @Override
    public void onResume(AppCompatActivity activity) {
        super.onResume(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onResume, true);
    }

    @Override
    public void onPause(AppCompatActivity activity) {
        super.onPause(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onPause, true);
    }

    @Override
    public void onSaveInstanceState(AppCompatActivity activity, Bundle bundle) {
        super.onSaveInstanceState(activity, bundle);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onSaveInstanceState, true);
    }

    @Override
    public void onStop(AppCompatActivity activity) {
        super.onStop(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onStop, true);
    }

    @Override
    public void onDestroy(AppCompatActivity activity) {
        super.onDestroy(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onDestroy, true);
    }

    @Override
    public void onNewIntent(AppCompatActivity activity, Intent intent) {
        super.onNewIntent(activity, intent);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onNewIntent, true);
    }

    @Override
    public boolean onOptionsItemSelected(AppCompatActivity activity, MenuItem item) {
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onOptionsItemSelected, true);
        return super.onOptionsItemSelected(activity, item);
    }
}
