package com.soundcloud.lightcycle.sample.real_world;

import dagger.Module;
import dagger.Provides;

import java.util.Calendar;

@Module(injects = HomeActivity.class)
class ApplicationModule {

    @Provides Calendar provideCalendar() {
        return Calendar.getInstance();
    }
}
