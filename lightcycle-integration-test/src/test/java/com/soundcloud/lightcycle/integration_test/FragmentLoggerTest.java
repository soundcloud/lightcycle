package com.soundcloud.lightcycle.integration_test;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.truth.BooleanSubject;
import com.soundcloud.lightcycle.integration_test.callback.FragmentLifecycleCallback;
import com.soundcloud.lightcycle.sample.basic.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentController;
import utils.FragmentTestHelper;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class FragmentLoggerTest {
    private final SampleFragment sampleFragment = new SampleFragment();
    private final FragmentLogger fragmentLogger = sampleFragment.fragmentLogger;
    private final FragmentController controller = FragmentController.of(sampleFragment);

    @Test
    public void onCreate() {
        controller.create();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated)
        );
    }

    @Test
    public void onStart() {
        controller.create()
                .start();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart)
        );
    }

    @Test
    public void onResume() {
        controller.create()
                .start()
                .resume();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onResume)
        );
    }

    @Test
    public void onPause() {
        controller.create()
                .start()
                .resume()
                .pause();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onResume,
                        FragmentLifecycleCallback.onPause)
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
                Arrays.asList(FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onResume,
                        FragmentLifecycleCallback.onPause,
                        FragmentLifecycleCallback.onSaveInstanceState)
        );
    }

    @Test
    public void onStop() {
        controller.create()
                .start()
                .stop();

        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onStop)
        );
    }

    @Test
    public void onDestroy() {
        controller.create()
                .destroy();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onDestroyView,
                        FragmentLifecycleCallback.onDestroy,
                        FragmentLifecycleCallback.onDetach)
        );
    }

    @Test
    public void bindFragmentOnlyOnceWhenReAttched() {
        FragmentTestHelper
                .from(sampleFragment)
                .addFragment()
                .removeFragment()
                .addFragment();

        assertThat(sampleFragment.onAttachCount).is(2);
        assertThat(sampleFragment.bindCount).is(1);
    }

    private void assertLifecycleCallbackCallIsCorrect(List<FragmentLifecycleCallback> callbacks) {
        for (FragmentLifecycleCallback fragmentLifecycleCallback : FragmentLifecycleCallback.values()) {
            BooleanSubject subject = assertThat(fragmentLogger.isFragmentLifecycleCallbackCalled(fragmentLifecycleCallback));
            if (callbacks.contains(fragmentLifecycleCallback)) {
                subject.isTrue();
            } else {
                subject.isFalse();
            }
        }
    }
}
