package com.soundcloud.lightcycle.sample.race.mike;

import com.soundcloud.lightcycle.LightCycleDispatcher;

import java.util.HashSet;
import java.util.Set;

class RaceLightCycleDispatcher implements LightCycleDispatcher<RaceLightCycle>, RaceLightCycle {
    private final Set<RaceLightCycle> raceLightCycles = new HashSet<>();

    @Override
    public void bind(RaceLightCycle race) {
        raceLightCycles.add(race);
    }

    @Override
    public void onStarted(Race race) {
        for (RaceLightCycle lightCycle : raceLightCycles) {
            lightCycle.onStarted(race);
        }
    }

    @Override
    public void onFinished(Race race) {
        for (RaceLightCycle lightCycle : raceLightCycles) {
            lightCycle.onFinished(race);
        }
    }
}
