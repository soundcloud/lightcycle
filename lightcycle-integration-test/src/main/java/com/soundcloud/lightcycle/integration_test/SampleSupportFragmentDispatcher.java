package com.soundcloud.lightcycle.integration_test;

import androidx.fragment.app.Fragment;

import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.SupportFragmentLightCycle;
import com.soundcloud.lightcycle.SupportFragmentLightCycleDispatcher;

class SampleSupportFragmentDispatcher extends SupportFragmentLightCycleDispatcher<Fragment> {
    @LightCycle DefaultSupportFragmentLightCycle<Fragment> lightCycle = new DefaultSupportFragmentLightCycle<>();

    int bindCount = 0;

    @Override
    public void bind(SupportFragmentLightCycle<Fragment> lightCycle) {
        super.bind(lightCycle);
        bindCount++;
    }
}
