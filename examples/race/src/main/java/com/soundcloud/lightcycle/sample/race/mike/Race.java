package com.soundcloud.lightcycle.sample.race.mike;

import com.soundcloud.lightcycle.LightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycles;

abstract class Race implements LightCycleDispatcher<RaceLightCycle> {
    private final String type;
    private final RaceLightCycleDispatcher raceLightCycleDispatcher;

    Race(String type) {
        this.type = type;
        this.raceLightCycleDispatcher = new RaceLightCycleDispatcher();
    }

    @Override
    public String toString() {
        return "Race{" +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public void bind(RaceLightCycle raceLightCycle) {
        raceLightCycleDispatcher.bind(raceLightCycle);
    }

    void run() {
        LightCycles.bind(this);
        onStarted(this);
        onFinished(this);
    }

    protected void onStarted(Race race) {
        raceLightCycleDispatcher.onStarted(race);
    }

    protected void onFinished(Race race) {
        raceLightCycleDispatcher.onFinished(race);
    }
}
