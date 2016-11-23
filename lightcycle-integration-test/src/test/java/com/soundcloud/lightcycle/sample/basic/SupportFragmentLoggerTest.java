package com.soundcloud.lightcycle.sample.basic;

import com.google.common.truth.BooleanSubject;
import com.soundcloud.lightcycle.sample.basic.callback.FragmentLifecycleCallback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentController;

import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class SupportFragmentLoggerTest {
    private final SampleSupportFragment sampleSupportFragment = new SampleSupportFragment();
    private final SupportFragmentLogger supportFragmentLogger = sampleSupportFragment.supportFragmentLogger;
    private final SupportFragmentController controller = SupportFragmentController.of(sampleSupportFragment);

    @Test
    public void onCreate() {
        controller.create();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate)
        );
    }

    @Test
    public void onStart() {
        controller.create()
                .start();
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated)
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

    private void assertLifecycleCallbackCallIsCorrect(List<FragmentLifecycleCallback> callbacks) {
        for (FragmentLifecycleCallback fragmentLifecycleCallback : FragmentLifecycleCallback.values()) {
            BooleanSubject subject = assertThat(supportFragmentLogger.isFragmentLifecycleCallbackCalled(fragmentLifecycleCallback));
            if (callbacks.contains(fragmentLifecycleCallback)) {
                subject.isTrue();
            } else {
                subject.isFalse();
            }
        }
    }

}