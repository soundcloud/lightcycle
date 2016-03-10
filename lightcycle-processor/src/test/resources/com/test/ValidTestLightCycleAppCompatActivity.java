package com.test;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycleAppCompatActivity;

import android.app.Activity;

public class ValidTestLightCycleAppCompatActivity extends LightCycleAppCompatActivity<ValidTestLightCycleAppCompatActivity> {
    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;

}

class LightCycle1 extends DefaultActivityLightCycle<ValidTestLightCycleAppCompatActivity> {
}

class LightCycle2 extends DefaultActivityLightCycle<ValidTestLightCycleAppCompatActivity> {
}
