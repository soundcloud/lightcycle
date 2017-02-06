package com.soundcloud.lightcycle.sample.real_world.home;

import com.soundcloud.lightcycle.ActivityLightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycle;

import javax.inject.Inject;

class HomePresenter extends ActivityLightCycleDispatcher<HomeView> {
    @LightCycle final HeaderPresenter headerPresenter;
    @LightCycle final DescriptionPresenter descriptionPresenter;

    @Inject
    HomePresenter(HeaderPresenter headerPresenter, DescriptionPresenter descriptionPresenter) {
        this.headerPresenter = headerPresenter;
        this.descriptionPresenter = descriptionPresenter;
    }
}
