package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.sample.real_world.home.HomeActivity;
import com.soundcloud.lightcycle.sample.real_world.license.LicenseFragment;
import dagger.Module;
import dagger.Provides;

import java.util.Calendar;

@Module(injects = {HomeActivity.class, LicenseFragment.class})
class ApplicationModule {

    @Provides Calendar provideCalendar() {
        return Calendar.getInstance();
    }
}
