package com.soundcloud.lightcycle.sample.basic;

import android.support.v7.app.AppCompatActivity;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.LightCycleBaseClass;
import com.soundcloud.lightcycle.LightCycleDispatcher;

@LightCycleBaseClass
public abstract class BaseActivity extends AppCompatActivity
        implements LightCycleDispatcher<ActivityLightCycle<AppCompatActivity>> {
}
