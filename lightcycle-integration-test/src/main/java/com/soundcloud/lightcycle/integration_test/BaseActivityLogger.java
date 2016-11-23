package com.soundcloud.lightcycle.integration_test;

import android.app.Activity;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.integration_test.callback.ActivityLifecycleCallback;

import java.util.HashMap;
import java.util.Map;

class BaseActivityLogger<T extends Activity> extends DefaultActivityLightCycle<T> {

    Map<ActivityLifecycleCallback, Boolean> lifecycleCallbackCallState;

    BaseActivityLogger() {
        initializeLifecycleCallbackCallStateMap();
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
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onNewIntent, false);
        this.lifecycleCallbackCallState.put(ActivityLifecycleCallback.onOptionsItemSelected, false);
    }

    boolean isActivityLifecycleCallbackCalled(ActivityLifecycleCallback callback) {
        return lifecycleCallbackCallState.get(callback);
    }
}
