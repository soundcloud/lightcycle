package com.soundcloud.lightcycle.sample.race.mike;

import com.soundcloud.lightcycle.LightCycle;

class ThreeLeggedRace extends Race {
    @LightCycle
    LoggerRaceLightCycle loggerRaceLightCycle = new LoggerRaceLightCycle();

    ThreeLeggedRace() {
        super("3-Legged");
    }
}
