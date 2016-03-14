package com.soundcloud.lightcycle;

import android.support.v4.app.Fragment;

// Because neither the processor or the lib (java libraries) can depend on the api (Android library),
// we have to create a fake `LightCycleSupportFragment` here for testing purpose.
public abstract class LightCycleSupportFragment<FragmentType extends Fragment>
        extends Fragment
        implements LightCycleDispatcher<SupportFragmentLightCycle<FragmentType>> {

    @Override
    public void bind(SupportFragmentLightCycle<FragmentType> lifeCycleComponent) { }

}
