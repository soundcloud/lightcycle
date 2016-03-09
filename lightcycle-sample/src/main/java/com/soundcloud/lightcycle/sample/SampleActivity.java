package com.soundcloud.lightcycle.sample;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.sample.tracker.ScreenTracker;

import javax.inject.Inject;

public class SampleActivity extends MyBaseActivity {
    @Inject @LightCycle ScreenTracker screenTracker;
    @Inject AppForegroundStateProvider foregroundStateProvider;

    public SampleActivity() {
        liftAndBind(foregroundStateProvider);
    }

    @Override
    public String getScreenName() {
        return "SampleScreenName";
    }

    @Override
    void setActivityContentView() {
        setContentView(R.layout.activity_sample);
    }
}
