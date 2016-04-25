package com.test;

import com.soundcloud.lightcycle.FragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.FragmentLightCycleDispatcher;
import com.soundcloud.lightcycle.DefaultFragmentLightCycle;

import android.app.Fragment;

public class ValidTestParameterizedDispatcher<SomeType> extends FragmentLightCycleDispatcher<Fragment>{

    @LightCycle DefaultFragmentLightCycle<Fragment> lightCycle1;
    @LightCycle DefaultFragmentLightCycle<Fragment> lightCycle2;

}

