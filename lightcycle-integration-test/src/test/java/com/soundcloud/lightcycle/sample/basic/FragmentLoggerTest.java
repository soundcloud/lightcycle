package com.soundcloud.lightcycle.sample.basic;

import android.os.Bundle;

import com.soundcloud.lightcycle.sample.basic.callback.FragmentLifecycleCallback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentController;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class FragmentLoggerTest {

    @Test
    public void verifyFragmentLifecycleCallback() {
        final SampleFragment sampleFragment = new SampleFragment();
        final FragmentLogger fragmentLogger = sampleFragment.fragmentLogger;
        final FragmentController controller = FragmentController.of(sampleFragment);
        final Bundle dummyBundle = new Bundle();

        controller.create();
        // In Robolectric's FragmentController, onAttach is called after FragmentController.create().
        // which is different with normal Fragment's lifecycle
        // reference : https://github.com/robolectric/robolectric/issues/1951
        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onAttach)).isTrue();

        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onCreate)).isTrue();

        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onViewCreated)).isTrue();

        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onActivityCreated)).isTrue();

        controller.start();
        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onStart)).isTrue();

        controller.resume();
        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onResume)).isTrue();

        controller.pause();
        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onPause)).isTrue();

        controller.saveInstanceState(dummyBundle);
        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onSaveInstanceState)).isTrue();

        controller.stop();
        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onStop)).isTrue();

        controller.destroy();
        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onDestroy)).isTrue();

        assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(FragmentLifecycleCallback.onDetach)).isTrue();
    }

}