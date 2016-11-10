package test;

import android.app.Activity;

import com.soundcloud.lightcycle.ActivityLightCycle;
import com.soundcloud.lightcycle.LightCycleBaseClass;
import com.soundcloud.lightcycle.LightCycleDispatcher;

@LightCycleBaseClass
public abstract class BaseActivityAsObject<T extends String> extends Activity
        implements LightCycleDispatcher<ActivityLightCycle<BaseActivityAsObject<T>>> {
}
