package com.soundcloud.lightcycle.sample.basic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleFragment;

public class SampleFragment extends LightCycleFragment<SampleFragment> {

    @LightCycle
    FragmentLogger fragmentLogger = new FragmentLogger();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }
}
