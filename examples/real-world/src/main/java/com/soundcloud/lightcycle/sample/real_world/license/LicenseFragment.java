package com.soundcloud.lightcycle.sample.real_world.license;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleSupportFragment;
import com.soundcloud.lightcycle.sample.real_world.R;
import com.soundcloud.lightcycle.sample.real_world.SampleApp;

import javax.inject.Inject;

public class LicenseFragment extends LightCycleSupportFragment<LicenseView> implements LicenseView {

    @Inject @LightCycle LicensePresenter licensePresenter;

    public LicenseFragment() {
        SampleApp.getObjectGraph().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_license, container, false);
    }

    @Override
    public void showLicense(String text) {
        ((TextView) getView().findViewById(R.id.license)).setText(text);
    }

}
