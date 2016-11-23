package com.soundcloud.lightcycle.sample.basic;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.soundcloud.lightcycle.sample.basic.callback.ActivityCallback;
import com.soundcloud.lightcycle.sample.basic.callback.ActivityLifecycleCallback;

public class ActivityLogger extends BaseActivityLogger<SampleActivity> {

    @Override
    public void onCreate(SampleActivity activity, Bundle bundle) {
        super.onCreate(activity, bundle);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onCreate, true);
    }

    @Override
    public void onStart(SampleActivity activity) {
        super.onStart(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onStart, true);
    }

    @Override
    public void onRestoreInstanceState(SampleActivity activity, Bundle bundle) {
        super.onRestoreInstanceState(activity, bundle);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onRestoreInstanceState, true);
    }

    @Override
    public void onResume(SampleActivity activity) {
        super.onResume(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onResume, true);
    }

    @Override
    public void onPause(SampleActivity activity) {
        super.onPause(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onPause, true);
    }

    @Override
    public void onSaveInstanceState(SampleActivity activity, Bundle bundle) {
        super.onSaveInstanceState(activity, bundle);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onSaveInstanceState, true);
    }

    @Override
    public void onStop(SampleActivity activity) {
        super.onStop(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onStop, true);
    }

    @Override
    public void onDestroy(SampleActivity activity) {
        super.onDestroy(activity);
        lifecycleCallbackCallState.put(ActivityLifecycleCallback.onDestroy, true);
    }

    @Override
    public void onNewIntent(SampleActivity activity, Intent intent) {
        super.onNewIntent(activity, intent);
        callbackCallState.put(ActivityCallback.onNewIntent, true);
    }

    @Override
    public boolean onOptionsItemSelected(SampleActivity activity, MenuItem item) {
        callbackCallState.put(ActivityCallback.onOptionsItemSelected, true);
        return super.onOptionsItemSelected(activity, item);
    }

}
