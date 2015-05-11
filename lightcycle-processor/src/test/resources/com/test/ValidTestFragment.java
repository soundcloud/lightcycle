package com.test;

import com.soundcloud.lightcycle.DefaultLightCycleActivity;
import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleInjector;
import com.soundcloud.lightcycle.LightCycleSupportFragment;

import android.os.Bundle;

public class ValidTestActivity extends DefaultLightCycleActivity {

    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LightCycleInjector.attach(this);
    }
}

class LightCycle1 extends DefaultSupportFragmentLightCycle {
}

class LightCycle2 extends DefaultSupportFragmentLightCycle {
}
