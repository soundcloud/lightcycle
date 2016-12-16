package com.soundcloud.lightcycle.integration_test;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleSupportFragment;
import com.soundcloud.lightcycle.SupportFragmentLightCycle;
import com.soundcloud.lightcycle.sample.basic.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SampleSupportFragment extends LightCycleSupportFragment<SampleSupportFragment> {

    @LightCycle
    SupportFragmentLogger supportFragmentLogger = new SupportFragmentLogger();
    public int onAttachCount;
    public int bindCount;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onAttachCount++;
    }

    @Override
    public void bind(SupportFragmentLightCycle<SampleSupportFragment> lifeCycleComponent) {
        super.bind(lifeCycleComponent);
        bindCount++;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }
}
