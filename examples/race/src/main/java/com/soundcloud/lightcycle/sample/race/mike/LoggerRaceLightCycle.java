package com.soundcloud.lightcycle.sample.race.mike;

public class LoggerRaceLightCycle implements RaceLightCycle {
    @Override
    public void onStarted(Race race) {
        System.out.println("onStarted race=" + race);
    }

    @Override
    public void onFinished(Race race) {
        System.out.println("onFinished race=" + race);
    }
}
