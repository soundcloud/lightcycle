package com.soundcloud.lightcycle.sample.real_world;

import android.app.Activity;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class IllegalLC extends DefaultActivityLightCycle<IllegalLC.OtherActivity> {

    private boolean isForeground;

    @Inject
    public IllegalLC() {
        isForeground = false;
    }

    @Override
    public void onResume(OtherActivity activity) {
        isForeground = true;
    }

    @Override
    public void onPause(OtherActivity activity) {
        isForeground = false;
    }

    public boolean isForeground() {
        return isForeground;
    }


    public static class OtherActivity extends Activity {

    }
}
