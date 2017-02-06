package com.soundcloud.lightcycle.integration_test;

import android.view.Menu;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleAppCompatActivity;
import com.soundcloud.lightcycle.sample.basic.R;

public class SampleAppCompatActivity extends LightCycleAppCompatActivity<SampleAppCompatActivity> {
    @LightCycle
    AppCompatActivityLogger appCompatActivityLogger = new AppCompatActivityLogger();

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
