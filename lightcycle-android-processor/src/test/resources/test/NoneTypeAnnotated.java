package test;

import android.app.Activity;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.LightCycleBaseClass;
import com.soundcloud.lightcycle.LightCycleDispatcher;

public abstract class NoneTypeAnnotated extends Activity
        implements LightCycleDispatcher<ActivityLightCycle<Activity>> {
    @LightCycleBaseClass
    private static final String WRONG = "Oh no!";
}
