package com.test;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycleSupportDialogFragment;

import android.app.Activity;

public class ValidTestLightCycleSupportDialogFragment extends LightCycleSupportDialogFragment<ValidTestLightCycleSupportDialogFragment> {
    @LightCycle DialogLightCycle1 lightCycle1;
    @LightCycle DialogLightCycle2 lightCycle2;

}

class DialogLightCycle1 extends DefaultSupportFragmentLightCycle<ValidTestLightCycleSupportDialogFragment> {
}

class DialogLightCycle2 extends DefaultSupportFragmentLightCycle<ValidTestLightCycleSupportDialogFragment> {
}
