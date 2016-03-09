package com.soundcloud.lightcycle.sample.basic;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleAppCompatActivity;

import android.os.Bundle;

public class SampleActivity extends LightCycleAppCompatActivity {
    @LightCycle ActivityLogger activityLogger = new ActivityLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_sample);
    }
}
