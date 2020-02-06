package com.soundcloud.lightcycle.integration_test;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.common.truth.BooleanSubject;
import com.soundcloud.lightcycle.integration_test.callback.FragmentLifecycleCallback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import utils.FragmentTestHelper;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class SupportFragmentLoggerTest {

    private final SampleSupportFragment sampleSupportFragment = new SampleSupportFragment();
    private SupportFragmentLogger supportFragmentLogger;
    private FragmentScenario controller;

    @Before
    public void setUp() {
        controller = FragmentScenario.launchInContainer(SampleSupportFragment.class);
        controller.onFragment(fragment -> {
                    supportFragmentLogger = ((SampleSupportFragment) fragment).supportFragmentLogger;
                }
        );
    }

    @Test
    public void onCreate() {
        controller.onFragment(fragment -> fragment.onCreate(Bundle.EMPTY));
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(
                        FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onResume
                )
        );
    }

    @Test
    public void onStop() {
        controller.onFragment(fragment -> {
            fragment.onStop();
        });
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(
                        FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onResume,
                        FragmentLifecycleCallback.onStop
                )
        );
    }

    @Test
    public void onDestroy() {
        controller.onFragment(fragment -> {
            fragment.onDestroy();
        });
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(
                        FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onResume,
                        FragmentLifecycleCallback.onDestroy
                )
        );
    }

    @Test
    public void onDetach() {
        controller.onFragment(fragment -> {
            fragment.onDetach();
        });
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(
                        FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onResume,
                        FragmentLifecycleCallback.onDetach
                )
        );
    }

    @Test
    public void bindFragmentOnlyOnceWhenReAttached() {
        FragmentTestHelper
                .from(sampleSupportFragment)
                .addFragment()
                .removeFragment()
                .addFragment();

        assertThat(sampleSupportFragment.onAttachCount).is(2);
        assertThat(sampleSupportFragment.bindCount).is(1);
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
