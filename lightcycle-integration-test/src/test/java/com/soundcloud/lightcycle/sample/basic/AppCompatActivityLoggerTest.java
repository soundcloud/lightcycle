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
public class AppCompatActivityLoggerTest {

    @Test
    public void verifyActivityLifecycleCallback() {
        final ActivityController<SampleAppCompatActivity> controller = Robolectric.buildActivity(SampleAppCompatActivity.class);
        final SampleAppCompatActivity sampleAppCompatActivity = controller.get();
        final AppCompatActivityLogger appCompatActivityLogger = sampleAppCompatActivity.appCompatActivityLogger;
        final Bundle dummyBundle = new Bundle();

        controller.create();
        assertThat(appCompatActivityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onCreate)).isTrue();

        controller.start();
        assertThat(appCompatActivityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onStart)).isTrue();

        controller.restoreInstanceState(dummyBundle);
        assertThat(appCompatActivityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onRestoreInstanceState)).isTrue();

        controller.resume();
        assertThat(appCompatActivityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onResume)).isTrue();

        controller.pause();
        assertThat(appCompatActivityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onPause)).isTrue();

        controller.saveInstanceState(dummyBundle);
        assertThat(appCompatActivityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onSaveInstanceState)).isTrue();

        controller.stop();
        assertThat(appCompatActivityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onStop)).isTrue();

        controller.destroy();
        assertThat(appCompatActivityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onDestroy)).isTrue();
    }


    @Test
    public void verifyActivityCallback() {
        final ActivityController<SampleAppCompatActivity> controller = Robolectric.buildActivity(SampleAppCompatActivity.class).setup();
        final SampleAppCompatActivity sampleAppCompatActivity = controller.get();
        final AppCompatActivityLogger appCompatActivityLogger = sampleAppCompatActivity.appCompatActivityLogger;

        controller.newIntent(new Intent());
        assertThat(appCompatActivityLogger.isActivityCallbackCalled(ActivityCallback.onNewIntent)).isTrue();

        ShadowActivity shadowActivity = Shadows.shadowOf(sampleAppCompatActivity);
        shadowActivity.clickMenuItem(R.id.action_search);
        assertThat(appCompatActivityLogger.isActivityCallbackCalled(ActivityCallback.onOptionsItemSelected)).isTrue();
    }
}
