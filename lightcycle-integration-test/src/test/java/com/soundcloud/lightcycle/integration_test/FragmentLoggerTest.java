package com.soundcloud.lightcycle.integration_test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
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
public class FragmentLoggerTest {

    private final SampleFragment sampleFragment = new SampleFragment();
    private FragmentLogger fragmentLogger;
    private FragmentScenario controller;

    @Before
    public void setUp() {
        controller = FragmentScenario.launchInContainer(SampleFragment.class);
        controller.onFragment(fragment -> {
                    fragmentLogger = ((SampleFragment) fragment).fragmentLogger;
                }
        );
    }

    @Test
    public void onCreate() {
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
    public void onPause() {
        controller.onFragment(fragment -> {
            fragment.onPause();
        });
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(
                        FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onResume,
                        FragmentLifecycleCallback.onPause
                )
        );
    }

    @Test
    public void onSaveInstanceState() {
        controller.onFragment(fragment -> {
            fragment.onPause();
            fragment.onSaveInstanceState(new Bundle());
        });
        assertLifecycleCallbackCallIsCorrect(
                Arrays.asList(
                        FragmentLifecycleCallback.onAttach,
                        FragmentLifecycleCallback.onCreate,
                        FragmentLifecycleCallback.onViewCreated,
                        FragmentLifecycleCallback.onActivityCreated,
                        FragmentLifecycleCallback.onStart,
                        FragmentLifecycleCallback.onResume,
                        FragmentLifecycleCallback.onPause,
                        FragmentLifecycleCallback.onSaveInstanceState
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
