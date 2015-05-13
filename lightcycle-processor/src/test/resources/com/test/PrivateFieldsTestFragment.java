package com.test;

import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.SupportFragmentLightCycle;

import android.support.v4.app.Fragment;

public class PrivateFieldsTestFragment extends Fragment implements LightCycleDispatcher<SupportFragmentLightCycle> {

    private @LightCycle LightCycle1 lightCycle1;
    private @LightCycle LightCycle2 lightCycle2;

    @Override
    public void bind(SupportFragmentLightCycle lightCycle) {
        // nop
    }

}

class LightCycle1 extends DefaultSupportFragmentLightCycle {
}

class LightCycle2 extends DefaultSupportFragmentLightCycle {
}
