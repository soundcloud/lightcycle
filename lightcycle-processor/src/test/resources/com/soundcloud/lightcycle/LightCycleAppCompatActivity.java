package com.soundcloud.lightcycle;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;

import android.app.Activity;

// Because neither the processor or the lib (java libraries) can depend on the api (Android library),
// we have to create a fake `LightCycleAppCompatActivity` here for testing purpose.
public class LightCycleAppCompatActivity<T extends Activity>
        extends Activity
        implements LightCycleDispatcher<ActivityLightCycle<T>>{

        @Override
        public void bind(ActivityLightCycle<T> lightCycle) { }
}
