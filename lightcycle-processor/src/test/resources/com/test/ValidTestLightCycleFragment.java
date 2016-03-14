package com.test;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.DefaultFragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycleFragment;

import android.app.Activity;

public class ValidTestLightCycleFragment extends LightCycleFragment<ValidTestLightCycleFragment> {
    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;

}

class LightCycle1 extends DefaultFragmentLightCycle<ValidTestLightCycleFragment> {
}

class LightCycle2 extends DefaultFragmentLightCycle<ValidTestLightCycleFragment> {
}
