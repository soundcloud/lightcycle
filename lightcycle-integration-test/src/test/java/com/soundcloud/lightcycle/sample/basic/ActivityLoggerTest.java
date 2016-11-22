package com.soundcloud.lightcycle.sample.basic;

import android.content.Intent;
import android.os.Bundle;

import com.soundcloud.lightcycle.sample.basic.callback.ActivityCallback;
import com.soundcloud.lightcycle.sample.basic.callback.ActivityLifecycleCallback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ActivityLoggerTest {

    @Test
    public void verifyActivityLifecycleCallback() {
        final ActivityController<SampleActivity> controller = Robolectric.buildActivity(SampleActivity.class);
        final SampleActivity sampleActivity = controller.get();
        final ActivityLogger activityLogger = sampleActivity.activityLogger;
        final Bundle dummyBundle = new Bundle();

        controller.create();
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onCreate)).isTrue();

        controller.start();
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onStart)).isTrue();

        controller.restoreInstanceState(dummyBundle);
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onRestoreInstanceState)).isTrue();

        controller.resume();
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onResume)).isTrue();

        controller.pause();
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onPause)).isTrue();

        controller.saveInstanceState(dummyBundle);
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onSaveInstanceState)).isTrue();

        controller.stop();
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onStop)).isTrue();

        controller.destroy();
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onDestroy)).isTrue();
    }


    @Test
    public void verifyActivityCallback() {
        final ActivityController<SampleActivity> controller = Robolectric.buildActivity(SampleActivity.class).setup();
        final SampleActivity sampleActivity = controller.get();
        final ActivityLogger activityLogger = sampleActivity.activityLogger;

        controller.newIntent(new Intent());
        assertThat(activityLogger.isActivityCallbackCalled(ActivityCallback.onNewIntent)).isTrue();

        ShadowActivity shadowActivity = Shadows.shadowOf(sampleActivity);
        shadowActivity.clickMenuItem(R.id.action_search);
        assertThat(activityLogger.isActivityCallbackCalled(ActivityCallback.onOptionsItemSelected)).isTrue();
    }
}