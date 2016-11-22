package com.soundcloud.lightcycle.sample.basic;

import android.os.Bundle;

import com.soundcloud.lightcycle.sample.basic.callback.FragmentLifecycleCallback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentController;
import org.robolectric.util.FragmentController;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class SupportFragmentLoggerTest {

    @Test
    public void verifyFragmentLifecycleCallback() {
        final SampleSupportFragment sampleFragment = new SampleSupportFragment();
        final SupportFragmentLogger supportFragmentLogger = sampleFragment.supportFragmentLogger;
        final SupportFragmentController controller = SupportFragmentController.of(sampleFragment);

        controller.create();
        // In Robolectric's FragmentController, onAttach is called after FragmentController.create().
        // which is different with normal Fragment's lifecycle
        // reference : https://github.com/robolectric/robolectric/issues/1951
        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onAttach)).isTrue();

        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onCreate)).isTrue();

        controller.start();

        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onViewCreated)).isTrue();

        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onActivityCreated)).isTrue();

        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onStart)).isTrue();

        controller.resume();
        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onResume)).isTrue();

        controller.pause();
        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onPause)).isTrue();

        controller.stop();
        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onStop)).isTrue();

        controller.destroy();
        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onDestroy)).isTrue();

        assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onDetach)).isTrue();
    }

}