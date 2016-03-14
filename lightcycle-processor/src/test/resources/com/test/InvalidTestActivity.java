package com.test;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.DefaultActivityLightCycle;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleDispatcher;

import android.app.Activity;

public class InvalidTestActivity extends Activity {

    @LightCycle LightCycle1 lightCycle1;
    @LightCycle LightCycle2 lightCycle2;


}

class LightCycle1 extends DefaultActivityLightCycle<InvalidTestActivity> {
}

class LightCycle2 extends DefaultActivityLightCycle<InvalidTestActivity> {
}
