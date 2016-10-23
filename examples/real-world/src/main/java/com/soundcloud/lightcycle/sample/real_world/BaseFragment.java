package com.soundcloud.lightcycle.sample.real_world;

import android.support.v4.app.Fragment;

import com.soundcloud.lightcycle.LightCycleBaseClass;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.SupportFragmentLightCycle;

@LightCycleBaseClass
public abstract class BaseFragment<F extends BaseFragment> extends Fragment
        implements LightCycleDispatcher<SupportFragmentLightCycle<F>> {
}
