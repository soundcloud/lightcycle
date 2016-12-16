package com.soundcloud.lightcycle.integration_test;

import com.soundcloud.lightcycle.FragmentLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleFragment;
import com.soundcloud.lightcycle.sample.basic.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SampleFragment extends LightCycleFragment<SampleFragment> {

    @LightCycle FragmentLogger fragmentLogger = new FragmentLogger();
    int bindCount = 0;
    int onAttachCount = 0;

    @Override
    public void bind(FragmentLightCycle<SampleFragment> lifeCycleComponent) {
        super.bind(lifeCycleComponent);
        bindCount++;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onAttachCount++;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }
}
