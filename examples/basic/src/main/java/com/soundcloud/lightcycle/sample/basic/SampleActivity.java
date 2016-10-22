package com.soundcloud.lightcycle.sample.basic;

import android.support.v7.app.AppCompatActivity;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleAppCompatActivity;

public class SampleActivity extends LightCycleAppCompatActivity<AppCompatActivity> {
    @LightCycle ActivityLogger activityLogger = new ActivityLogger();

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_sample);
    }
}
