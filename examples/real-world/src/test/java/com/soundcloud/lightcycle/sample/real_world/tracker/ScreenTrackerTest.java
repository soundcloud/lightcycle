package com.soundcloud.lightcycle.sample.real_world.tracker;

import static org.mockito.Mockito.verify;

import com.soundcloud.lightcycle.sample.real_world.home.HomeActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ScreenTrackerTest {

    @Mock private TrackingOperations operations;
    @Mock private HomeActivity activity;
    private ScreenTracker tracker;

    @Before
    public void setUp() throws Exception {
        tracker = new ScreenTracker(operations);
    }

    @Test
    public void trackScreenOnResume() {
        tracker.onResume(activity);

        verify(operations).trackScreen(activity);
    }
}
