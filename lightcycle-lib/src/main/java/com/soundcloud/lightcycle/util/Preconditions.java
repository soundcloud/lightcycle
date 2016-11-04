package com.soundcloud.lightcycle.util;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.FragmentLightCycle;
import com.soundcloud.lightcycle.SupportFragmentLightCycle;

/**
 * Created by yuwono-niko on 11/4/16.
 */

public final class Preconditions {

    private static final String BINDING_TARGET_NULL_ERROR_MESSAGE = "Binding target must not be null";

    public static void checkBindingTarget(final ActivityLightCycle activityLightCycle) {
        if (activityLightCycle == null) {
            throw new NullPointerException(BINDING_TARGET_NULL_ERROR_MESSAGE);
        }
    }

    public static void checkBindingTarget(final FragmentLightCycle fragmentLightCycle) {
        if (fragmentLightCycle == null) {
            throw new NullPointerException(BINDING_TARGET_NULL_ERROR_MESSAGE);
        }
    }

    public static void checkBindingTarget(final SupportFragmentLightCycle supportFragmentLightCycle) {
        if (supportFragmentLightCycle == null) {
            throw new NullPointerException(BINDING_TARGET_NULL_ERROR_MESSAGE);
        }
    }
}
