package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleSupportFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

public class LicenseFragment extends LightCycleSupportFragment<LicenseFragment> {
    @Inject @LightCycle LicensePresenter descriptionPresenter;

    public LicenseFragment() {
        SampleApp.getObjectGraph().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_license, container, false);
    }

    void showLicense(String readme) {
        ((TextView) getView().findViewById(R.id.license)).setText(readme);
    }

}
