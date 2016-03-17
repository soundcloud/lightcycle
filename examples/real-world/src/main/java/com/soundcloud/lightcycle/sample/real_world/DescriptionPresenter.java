package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.DefaultActivityLightCycle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

class DescriptionPresenter extends DefaultActivityLightCycle<HomeActivity> {

    @Inject
    public DescriptionPresenter() {}

    @Override
    public void onCreate(HomeActivity activity, @Nullable Bundle bundle) {
        activity.showDescription("LightCycle", "https://github.com/soundcloud/lightcycle");
    }

}
