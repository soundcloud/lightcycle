package com.soundcloud.lightcycle.sample.real_world.experiment;

public interface MyLightCycle<ViewType> {

    void onScreen(ViewType host);

    void offScreen(ViewType host);
}
