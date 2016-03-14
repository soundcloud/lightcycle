package com.soundcloud.lightcycle;

import android.app.Fragment;

// Because neither the processor or the lib (java libraries) can depend on the api (Android library),
// we have to create a fake `LightCycleFragment` here for testing purpose.
public abstract class LightCycleFragment<FragmentType extends Fragment>
        extends Fragment
        implements LightCycleDispatcher<FragmentLightCycle<FragmentType>> {


    @Override
    public void bind(FragmentLightCycle<FragmentType> lifeCycleComponent) { }

}
