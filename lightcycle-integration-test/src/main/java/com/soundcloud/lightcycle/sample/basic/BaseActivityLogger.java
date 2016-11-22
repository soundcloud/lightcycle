package com.soundcloud.lightcycle.sample.basic;

import android.app.Activity;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.sample.basic.callback.ActivityCallback;
import com.soundcloud.lightcycle.sample.basic.callback.ActivityLifecycleCallback;

import java.util.HashMap;
import java.util.Map;

class BaseActivityLogger<T extends Activity> extends DefaultActivityLightCycle<T> {

    Map<ActivityLifecycleCallback, Boolean> lifecycleCallbackCallState;
    Map<ActivityCallback, Boolean> callbackCallState;

    BaseActivityLogger() {
        initializeLifecycleCallbackCallStateMap();
        initializeCallbackCallStateMap();
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

    boolean isActivityLifecycleCallbackCalled(ActivityLifecycleCallback callback) {
        return lifecycleCallbackCallState.get(callback);
    }

    boolean isActivityCallbackCalled(ActivityCallback callback) {
        return callbackCallState.get(callback);
    }
}
