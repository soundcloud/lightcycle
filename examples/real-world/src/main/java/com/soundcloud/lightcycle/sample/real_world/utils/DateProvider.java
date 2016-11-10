package com.soundcloud.lightcycle.sample.real_world.utils;

import android.support.annotation.VisibleForTesting;

import java.util.Calendar;

import javax.inject.Inject;

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

    public boolean isMorning() {
        final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        return hourOfDay >= 0 && hourOfDay < 12;
    }
}
