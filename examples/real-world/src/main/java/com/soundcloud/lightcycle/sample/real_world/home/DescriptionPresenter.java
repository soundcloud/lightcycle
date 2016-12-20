package com.soundcloud.lightcycle.sample.real_world.home;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

class DescriptionPresenter extends DefaultActivityLightCycle<HomeView> {

    @Inject
    public DescriptionPresenter() {}

    @Override
    public void onCreate(HomeView homeView, @Nullable Bundle bundle) {
        homeView.showDescription("LightCycle", "https://github.com/soundcloud/lightcycle");
    }

}
