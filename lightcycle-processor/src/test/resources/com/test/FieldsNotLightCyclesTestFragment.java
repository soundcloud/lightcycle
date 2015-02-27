package com.test;

import com.soundcloud.android.lightcycle.LightCycle;
import com.soundcloud.android.lightcycle.LightCycleInjector;
import com.soundcloud.android.lightcycle.LightCycleSupportFragment;

import android.os.Bundle;

public class FieldsNotLightCyclesTestFragment extends LightCycleSupportFragment {

    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LightCycleInjector.attach(this);
    }
}

class LightCycle1 {
}

class LightCycle2 {
}
