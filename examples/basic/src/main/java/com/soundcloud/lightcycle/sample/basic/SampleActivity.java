package com.soundcloud.lightcycle.sample.basic;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.soundcloud.lightcycle.LightCycle;

public class SampleActivity extends LightCycle_BaseActivity {
    @LightCycle ActivityLogger activityLogger = new ActivityLogger();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_sample);
    }
}
