package com.soundcloud.lightcycle;

// Because neither the processor or the lib (java libraries) can depend on the api (Android library),
// we have to create a fake `LightCycleFragment` here for testing purpose.
public interface LightCycleDispatcher<LightCycle> {

    void bind(LightCycle lightCycle);

}
