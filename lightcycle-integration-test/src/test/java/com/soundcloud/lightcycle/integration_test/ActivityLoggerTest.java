package com.soundcloud.lightcycle.integration_test;

import android.content.Intent;
import android.os.Bundle;

import com.google.common.truth.BooleanSubject;
import com.soundcloud.lightcycle.integration_test.callback.ActivityLifecycleCallback;
import com.soundcloud.lightcycle.sample.basic.BuildConfig;
import com.soundcloud.lightcycle.sample.basic.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class ActivityLoggerTest {
    private final ActivityController<SampleActivity> controller = Robolectric.buildActivity(SampleActivity.class);
    private final SampleActivity sampleActivity = controller.get();
    private final ActivityLogger activityLogger = sampleActivity.activityLogger;

    @Test
    public void onCreate() {
        controller.create();
        assertLifecycleCallbackCallIsCorrect(
                Collections.singletonList(ActivityLifecycleCallback.onCreate)
        );
    }

    @Test
    public void onStart() {
        controller.create()
                .start();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(ActivityLifecycleCallback.onCreate,
                        ActivityLifecycleCallback.onStart)
        );
    }

    @Test
    public void onRestoreInstance() {
        controller.create()
                .start()
                .restoreInstanceState(new Bundle());
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(ActivityLifecycleCallback.onCreate,
                        ActivityLifecycleCallback.onStart,
                        ActivityLifecycleCallback.onRestoreInstanceState)
        );
    }

    @Test
    public void onResume() {
        controller.create()
                .start()
                .resume();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(ActivityLifecycleCallback.onCreate,
                        ActivityLifecycleCallback.onStart,
                        ActivityLifecycleCallback.onResume)
        );
    }

    @Test
    public void onPause() {
        controller.create()
                .start()
                .resume()
                .pause();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(ActivityLifecycleCallback.onCreate,
                        ActivityLifecycleCallback.onStart,
                        ActivityLifecycleCallback.onResume,
                        ActivityLifecycleCallback.onPause)
        );
    }

    @Test
    public void onSaveInstanceState() {
        controller.create()
                .start()
                .resume()
                .pause()
                .saveInstanceState(new Bundle());
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(ActivityLifecycleCallback.onCreate,
                        ActivityLifecycleCallback.onStart,
                        ActivityLifecycleCallback.onResume,
                        ActivityLifecycleCallback.onPause,
                        ActivityLifecycleCallback.onSaveInstanceState)
        );
    }

    @Test
    public void onStop() {
        controller.create()
                .start()
                .stop();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(ActivityLifecycleCallback.onCreate,
                        ActivityLifecycleCallback.onStart,
                        ActivityLifecycleCallback.onStop)
        );
    }

    @Test
    public void onDestroy() {
        controller.create()
                .destroy();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(ActivityLifecycleCallback.onCreate,
                        ActivityLifecycleCallback.onDestroy)
        );
    }

    @Test
    public void onNewIntent() {
        controller.setup()
                .newIntent(new Intent());
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onNewIntent)).isTrue();
    }

    @Test
    public void onOptionsItemSelected() {
        controller.setup();
        ShadowActivity shadowActivity = Shadows.shadowOf(controller.get());
        shadowActivity.clickMenuItem(R.id.action_search);
        assertThat(activityLogger.isActivityLifecycleCallbackCalled(ActivityLifecycleCallback.onOptionsItemSelected)).isTrue();
    }

    private void assertLifecycleCallbackCallIsCorrect(List<ActivityLifecycleCallback> callbacks) {
        for (ActivityLifecycleCallback activityLifecycleCallback : ActivityLifecycleCallback.values()) {
            BooleanSubject subject = assertThat(activityLogger.isActivityLifecycleCallbackCalled(activityLifecycleCallback));
            if (callbacks.contains(activityLifecycleCallback)) {
                subject.isTrue();
            } else {
                subject.isFalse();
            }
        }
    }
}
