package com.test;

import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;
import com.soundcloud.lightcycle.FragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.DefaultFragmentLightCycle;

import android.app.Fragment;

public class ValidTestFragment extends Fragment implements LightCycleDispatcher<FragmentLightCycle<ValidTestFragment>> {

    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;

    @Override
    public void bind(FragmentLightCycle<ValidTestFragment> lightCycle) {
        // nop
    }

}

class LightCycle1 extends DefaultFragmentLightCycle<Fragment> {
}

class LightCycle2 extends DefaultFragmentLightCycle<Fragment> {
}
