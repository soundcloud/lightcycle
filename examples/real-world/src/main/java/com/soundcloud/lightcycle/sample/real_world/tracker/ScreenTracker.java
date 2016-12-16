package com.soundcloud.lightcycle.sample.real_world.tracker;

import com.soundcloud.lightcycle.sample.real_world.experiment.MyLightCycle;
import com.soundcloud.lightcycle.sample.real_world.home.HomeView;

import javax.inject.Inject;

// TODO: 12/16/16 Type lifting to use Screen instead of HomeView?
public class ScreenTracker implements MyLightCycle<HomeView> {

    private final TrackingOperations operations;

    @Inject
    public ScreenTracker(TrackingOperations operations) {
        this.operations = operations;
    }

    @Override
    public void onScreen(HomeView screen) {
        operations.trackScreen(screen);
    }

    @Override
    public void offScreen(HomeView host) {

    }
}
