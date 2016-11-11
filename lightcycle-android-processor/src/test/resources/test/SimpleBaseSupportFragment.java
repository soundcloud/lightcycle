package test;

import android.support.v4.app.Fragment;

import com.soundcloud.lightcycle.LightCycleBaseClass;
import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.SupportFragmentLightCycle;

@LightCycleBaseClass
public abstract class SimpleBaseSupportFragment extends Fragment
        implements LightCycleDispatcher<SupportFragmentLightCycle<Fragment>> {
}
