package test;

import android.app.Activity;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.LightCycleBaseClass;
import com.soundcloud.lightcycle.LightCycleDispatcher;

@LightCycleBaseClass
public interface BaseClassAnnotatedInterface extends LightCycleDispatcher<ActivityLightCycle<Activity>> {
}
