package com.soundcloud.lightcycle.sample.basic;

import android.view.Menu;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleActivity;
import com.soundcloud.lightcycle.LightCycleDispatcher;

public class SampleActivity
        extends LightCycleActivity<SampleActivity>
        implements LightCycleDispatcher<ActivityLightCycle<SampleActivity>> {

    @LightCycle
    ActivityLogger activityLogger = new ActivityLogger();

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_sample);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }
}