package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;

import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

class LicensePresenter extends DefaultSupportFragmentLightCycle<LicenseFragment> {

    @Inject
    public LicensePresenter() {
    }

    @Override
    public void onViewCreated(LicenseFragment fragment, View view, Bundle savedInstanceState) {
        fragment.showLicense("Apache License 2.0");
    }
}
