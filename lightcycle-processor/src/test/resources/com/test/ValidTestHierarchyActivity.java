package com.test;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;

import android.app.Activity;

public class ValidTestHierarchyActivity  extends BaseActivity {
    @LightCycle LightCycle2 lightCycle2;

}

class BaseActivity extends Activity implements LightCycleDispatcher<ActivityLightCycle<BaseActivity>> {

    @LightCycle LightCycle1 lightCycle1;

    @Override
    public void bind(ActivityLightCycle<BaseActivity> lightCycle) {
        // nop
    }

}

class LightCycle1 extends DefaultActivityLightCycle {
}

class LightCycle2 extends DefaultActivityLightCycle {
}
