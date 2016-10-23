package com.soundcloud.lightcycle.sample.real_world;

import android.support.v7.app.AppCompatActivity;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.LightCycleBaseClass;
import com.soundcloud.lightcycle.LightCycleDispatcher;

@LightCycleBaseClass
public abstract class BaseActivity<A extends BaseActivity> extends AppCompatActivity
        implements LightCycleDispatcher<ActivityLightCycle<A>> {
}
