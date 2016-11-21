package com.soundcloud.lightcycle.sample.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ActivityLoggerTest {

    @Test
    public void verifyActivityCallback() {
        final ActivityController<SampleActivity> controller = Robolectric.buildActivity(SampleActivity.class);
        final SampleActivity sampleActivity = controller.get();
        final ActivityLogger activityLogger = sampleActivity.activityLogger;

        controller.create();
        assertThat(activityLogger.isActivityCallbackCalled(ActivityLogger.ActivityLifecycleCallback.onCreate)).isTrue();

        controller.start();
        assertThat(activityLogger.isActivityCallbackCalled(ActivityLogger.ActivityLifecycleCallback.onStart)).isTrue();

        controller.resume();
        assertThat(activityLogger.isActivityCallbackCalled(ActivityLogger.ActivityLifecycleCallback.onResume)).isTrue();

        controller.pause();
        assertThat(activityLogger.isActivityCallbackCalled(ActivityLogger.ActivityLifecycleCallback.onPause)).isTrue();

        controller.stop();
        assertThat(activityLogger.isActivityCallbackCalled(ActivityLogger.ActivityLifecycleCallback.onStop)).isTrue();

        controller.destroy();
        assertThat(activityLogger.isActivityCallbackCalled(ActivityLogger.ActivityLifecycleCallback.onDestroy)).isTrue();
    }

}
