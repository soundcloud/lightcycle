package test;

import android.app.Fragment;

import com.soundcloud.lightcycle.FragmentLightCycle;
import com.soundcloud.lightcycle.LightCycleBaseClass;
import com.soundcloud.lightcycle.LightCycleDispatcher;

@LightCycleBaseClass
public abstract class BaseFragmentAsObject<T extends String> extends Fragment
        implements LightCycleDispatcher<FragmentLightCycle<BaseFragmentAsObject<T>>> {
}
