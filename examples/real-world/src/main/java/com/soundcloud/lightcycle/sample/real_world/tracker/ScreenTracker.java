package com.soundcloud.lightcycle.sample.real_world.tracker;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.sample.real_world.HomeActivity;

import javax.inject.Inject;

public class ScreenTracker extends DefaultActivityLightCycle<HomeActivity> {

    private final TrackingOperations operations;

    @Inject
    public ScreenTracker(TrackingOperations operations) {
        this.operations = operations;
    }

    @Override
    public void onResume(HomeActivity activity) {
        operations.trackScreen(activity);
    }
}
