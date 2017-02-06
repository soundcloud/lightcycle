package com.soundcloud.lightcycle.util;

import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycles;

public class LightCycleBinderHelper {

    private final LightCycleDispatcher<?> dispatcher;
    private boolean isBound = false;

    public LightCycleBinderHelper(LightCycleDispatcher<?> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void bindIfNecessary() {
        if (!isBound) {
            LightCycles.bind(dispatcher);
            isBound = true;
        }
    }
}
