package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.sample.real_world.tracker.ScreenTracker;

import javax.inject.Inject;

public class SampleActivity extends MyBaseActivity {
    @Inject @LightCycle ScreenTracker screenTracker;
    @Inject @LightCycle AppForegroundStateProvider foregroundStateProvider;

    @Override
    public String getScreenName() {
        return "SampleScreenName";
    }

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_sample);
    }
}
