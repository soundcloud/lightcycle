package com.test;

import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.SupportFragmentLightCycle;

import android.support.v4.app.Fragment;

public class ValidTestSupportFragment extends Fragment implements LightCycleDispatcher<SupportFragmentLightCycle<ValidTestSupportFragment>> {

    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;

    @Override
    public void bind(SupportFragmentLightCycle<ValidTestSupportFragment> lightCycle) {
        // nop
    }

}

class LightCycle1 extends DefaultSupportFragmentLightCycle<ValidTestSupportFragment> {
}

class LightCycle2 extends DefaultSupportFragmentLightCycle<ValidTestSupportFragment> {
}
