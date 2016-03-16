package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.sample.real_world.utils.DateProvider;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

class HeaderPresenter extends DefaultActivityLightCycle<HomeActivity> {
    private final DateProvider dateProvider;

    @Inject
    public HeaderPresenter(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    @Override
    public void onCreate(HomeActivity activity, @Nullable Bundle bundle) {
        if (dateProvider.isMorning()) {
            activity.sayHello(R.string.good_morning);
        } else {
            activity.sayHello(R.string.hello);
        }
    }

}
