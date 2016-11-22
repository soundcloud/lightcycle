package com.soundcloud.lightcycle.sample.basic;

import android.view.Menu;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleAppCompatActivity;

public class SampleActivity extends LightCycleAppCompatActivity<SampleActivity> {
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
