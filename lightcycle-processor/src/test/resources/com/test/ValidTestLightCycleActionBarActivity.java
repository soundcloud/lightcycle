package com.test;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycleActionBarActivity;

import android.app.Activity;

public class ValidTestLightCycleActionBarActivity extends LightCycleActionBarActivity<ValidTestLightCycleActionBarActivity> {
    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;

}

class LightCycle1 extends DefaultActivityLightCycle<ValidTestLightCycleActionBarActivity> {
}

class LightCycle2 extends DefaultActivityLightCycle<ValidTestLightCycleActionBarActivity> {
}
