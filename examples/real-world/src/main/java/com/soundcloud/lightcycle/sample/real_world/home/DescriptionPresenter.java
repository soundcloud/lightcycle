package com.soundcloud.lightcycle.sample.real_world.home;

import com.soundcloud.lightcycle.sample.real_world.experiment.MyLightCycle;

import javax.inject.Inject;

class DescriptionPresenter implements MyLightCycle<HomeView> {

    @Inject
    public DescriptionPresenter() {}

    @Override
    public void onScreen(HomeView homeView) {
        homeView.showDescription("LightCycle", "https://github.com/soundcloud/lightcycle");
    }

    @Override
    public void offScreen(HomeView host) {

    }
}
