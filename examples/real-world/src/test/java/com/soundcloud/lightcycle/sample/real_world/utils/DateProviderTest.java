package com.soundcloud.lightcycle.sample.real_world.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DateProviderTest {
    private DateProvider dateProvider;
    @Mock private Calendar calendar;

    @Before
    public void setUp() throws Exception {
        dateProvider = new DateProvider(calendar);
    }

    @Test
    public void midnightIsMorning() {
        when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(0);

        assertThat(dateProvider.isMorning(), is(true));
    }

    @Test
    public void lunchTimeIsNotAnyMoreMorning() {
        when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(12);

        assertThat(dateProvider.isMorning(), is(false));
    }

    @Test
    public void dinerTimeIsNotMorning() {
        when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(20);

        assertThat(dateProvider.isMorning(), is(false));
    }
}
