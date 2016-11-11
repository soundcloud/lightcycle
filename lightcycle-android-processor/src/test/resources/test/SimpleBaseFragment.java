package test;

import android.app.Fragment;

import com.soundcloud.lightcycle.FragmentLightCycle;
import com.soundcloud.lightcycle.LightCycleBaseClass;
import com.soundcloud.lightcycle.LightCycleDispatcher;

@LightCycleBaseClass
public abstract class SimpleBaseFragment extends Fragment
        implements LightCycleDispatcher<FragmentLightCycle<Fragment>> {
}
