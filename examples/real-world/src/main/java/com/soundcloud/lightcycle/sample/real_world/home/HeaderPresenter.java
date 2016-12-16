package com.soundcloud.lightcycle.sample.real_world.home;

import com.soundcloud.lightcycle.sample.real_world.R;
import com.soundcloud.lightcycle.sample.real_world.experiment.MyLightCycle;
import com.soundcloud.lightcycle.sample.real_world.utils.DateProvider;

import javax.inject.Inject;

class HeaderPresenter implements MyLightCycle<HomeView> {
    private final DateProvider dateProvider;

    @Inject
    public HeaderPresenter(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    @Override
    public void onScreen(HomeView homeView) {
        if (dateProvider.isMorning()) {
            homeView.sayHello(R.string.good_morning);
        } else {
            homeView.sayHello(R.string.hello);
        }
    }

    @Override
    public void offScreen(HomeView host) {

    }
}
