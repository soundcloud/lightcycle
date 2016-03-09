package com.test;

import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;
import com.soundcloud.lightcycle.FragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.SupportFragmentLightCycle;

import android.support.v4.app.Fragment;

public class PrivateFieldsTestFragment extends Fragment implements LightCycleDispatcher<FragmentLightCycle> {

    private @LightCycle LightCycle1 lightCycle1;
    private @LightCycle LightCycle2 lightCycle2;

    @Override
    public void bind(FragmentLightCycle lightCycle) {
        // nop
    }

}

class LightCycle1 extends DefaultSupportFragmentLightCycle<Fragment> {
}

class LightCycle2 extends DefaultSupportFragmentLightCycle<Fragment> {
}
