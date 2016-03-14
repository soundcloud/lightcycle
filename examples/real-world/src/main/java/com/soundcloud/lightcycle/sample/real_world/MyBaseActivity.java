package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.LightCycleAppCompatActivity;
import com.soundcloud.lightcycle.sample.real_world.tracker.Screen;

// Implement you base activity to wire things up or use one of the provide default activities (like LightCycleAppCompatActivity).
public abstract class MyBaseActivity extends LightCycleAppCompatActivity<MyBaseActivity> implements Screen {

    public MyBaseActivity() {
        SampleApp.getObjectGraph().inject(this);
    }

}
