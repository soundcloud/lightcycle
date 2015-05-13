package com.test;

import com.soundcloud.android.lightcycle.LightCycle;
import com.soundcloud.android.lightcycle.LightCycleDispatcher;
import com.soundcloud.android.lightcycle.SupportFragmentLightCycle;

import android.support.v4.app.Fragment;

public class FieldsNotLightCyclesTestFragment extends Fragment implements LightCycleDispatcher<SupportFragmentLightCycle> {

    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;

    @Override
    public void bind(SupportFragmentLightCycle lightCycle) {
        // nop
    }

}

class LightCycle1 {
}

class LightCycle2 {
}
