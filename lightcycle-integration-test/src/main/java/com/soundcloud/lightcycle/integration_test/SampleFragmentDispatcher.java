package com.soundcloud.lightcycle.integration_test;

import com.soundcloud.lightcycle.DefaultFragmentLightCycle;
import com.soundcloud.lightcycle.FragmentLightCycle;
import com.soundcloud.lightcycle.FragmentLightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycle;

import android.app.Fragment;

class SampleFragmentDispatcher extends FragmentLightCycleDispatcher<Fragment> {
    @LightCycle DefaultFragmentLightCycle<Fragment> lightCycle = new DefaultFragmentLightCycle<>();

    int bindCount = 0;

    @Override
    public void bind(FragmentLightCycle<Fragment> lightCycle) {
        super.bind(lightCycle);
        bindCount++;
    }
}
