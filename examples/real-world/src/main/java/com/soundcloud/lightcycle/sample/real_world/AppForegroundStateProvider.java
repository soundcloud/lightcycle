package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;

import android.app.Activity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class AppForegroundStateProvider extends DefaultActivityLightCycle<Activity> {

    private boolean isForeground;

    @Inject
    public AppForegroundStateProvider() {
        isForeground = false;
    }

    @Override
    public void onResume(Activity activity) {
        isForeground = true;
    }

    @Override
    public void onPause(Activity activity) {
        isForeground = false;
    }

    public boolean isForeground() {
        return isForeground;
    }

}
