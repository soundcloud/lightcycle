package com.soundcloud.lightcycle.sample.real_world.experiment;

import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycles;
import com.soundcloud.lightcycle.sample.real_world.home.HomeView;

import java.util.HashSet;
import java.util.Set;

// TODO: 12/16/16 <HostType> (processor cannot handle it now)
public class MyLightCycleDispatcher
        implements LightCycleDispatcher<MyLightCycle<HomeView>>, MyLightCycle<HomeView> {

    private final Set<MyLightCycle<HomeView>> lightCycles;

    public MyLightCycleDispatcher() {
        lightCycles = new HashSet<>();
    }

    @Override
    public void bind(MyLightCycle<HomeView> homeViewMyLightCycle) {
        lightCycles.add(homeViewMyLightCycle);
    }

    @Override
    public void onScreen(HomeView host) {
        LightCycles.bind(this);
        for (MyLightCycle<HomeView> lightCycle : lightCycles) {
            lightCycle.onScreen(host);
        }
    }

    @Override
    public void offScreen(HomeView host) {
        for (MyLightCycle<HomeView> lightCycle : lightCycles) {
            lightCycle.offScreen(host);
        }
    }
}
