package com.soundcloud.lightcycle.sample.real_world.utils;

import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;
import java.util.Calendar;

public class DateProvider {

    private final Calendar calendar;

    @Inject
    public DateProvider(Calendar calendar) {
        this.calendar = calendar;
    }

    @VisibleForTesting
    public DateProvider() {
        this.calendar = null;
    }


    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public boolean isMorning() {
        final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        return hourOfDay >= 0 && hourOfDay < 12;
    }
}
