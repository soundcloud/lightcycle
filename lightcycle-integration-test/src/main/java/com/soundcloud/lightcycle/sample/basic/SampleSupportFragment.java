package com.soundcloud.lightcycle.sample.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleSupportFragment;

public class SampleSupportFragment extends LightCycleSupportFragment<SampleSupportFragment> {

    @LightCycle
    SupportFragmentLogger supportFragmentLogger = new SupportFragmentLogger();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }
}
