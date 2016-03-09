package com.soundcloud.lightcycle.sample.real_world.tracker;

import javax.inject.Inject;

class TrackingOperations {
    @Inject
    public TrackingOperations() {
    }

    public void trackScreen(Screen screen) {
        System.out.println("Tracking screen:" + screen.getScreenName());
    }
}
