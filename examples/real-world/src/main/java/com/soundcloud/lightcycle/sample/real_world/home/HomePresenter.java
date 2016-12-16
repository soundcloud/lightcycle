package com.soundcloud.lightcycle.sample.real_world.home;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.sample.real_world.experiment.MyLightCycleDispatcher;

import javax.inject.Inject;

class HomePresenter extends MyLightCycleDispatcher {
    @LightCycle final HeaderPresenter headerPresenter;
    @LightCycle final DescriptionPresenter descriptionPresenter;

    @Inject
    HomePresenter(HeaderPresenter headerPresenter, DescriptionPresenter descriptionPresenter) {
        this.headerPresenter = headerPresenter;
        this.descriptionPresenter = descriptionPresenter;
    }
}
