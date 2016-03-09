package com.soundcloud.lightcycle.sample.real_world.tracker;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.sample.real_world.MyBaseActivity;

import javax.inject.Inject;

public class ScreenTracker extends DefaultActivityLightCycle<MyBaseActivity> {

    private final TrackingOperations operations;

    @Inject
    public ScreenTracker(TrackingOperations operations) {
        this.operations = operations;
    }

    @Override
    public void onResume(MyBaseActivity activity) {
        operations.trackScreen(activity);
    }
}
