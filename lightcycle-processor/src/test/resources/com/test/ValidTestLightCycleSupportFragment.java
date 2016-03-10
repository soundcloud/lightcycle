package com.test;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycleSupportFragment;

import android.app.Activity;

public class ValidTestLightCycleSupportFragment extends LightCycleSupportFragment<ValidTestLightCycleSupportFragment> {
    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;

}

class LightCycle1 extends DefaultSupportFragmentLightCycle<ValidTestLightCycleSupportFragment> {
}

class LightCycle2 extends DefaultSupportFragmentLightCycle<ValidTestLightCycleSupportFragment> {
}
