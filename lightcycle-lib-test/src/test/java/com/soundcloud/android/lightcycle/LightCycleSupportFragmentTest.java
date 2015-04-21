package com.soundcloud.android.lightcycle;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

@RunWith(LightCycleTestRunner.class)
public class LightCycleSupportFragmentTest {

    private @Mock SupportFragmentLightCycle lightCycle;
    private LightCycleSupportFragment fragment;
    private ActivityController<FragmentActivity> activityController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        // registers all life cycle components, so need to do it before every test
        fragment = new LightCycleSupportFragment();
        fragment.bind(lightCycle);
        activityController = attach(fragment);
    }

    @Test
    public void shouldForwardOnCreateAndBindItself() {
        verify(lightCycle).onCreate(fragment, null);
    }

    @Test
    public void shouldForwardOnDestroy() {
        activityController.destroy();

        verify(lightCycle).onDestroy(fragment);
    }

    @Test
    public void shouldForwardOnStart() {
        activityController.start();

        verify(lightCycle).onStart(fragment);
    }

    @Test
    public void shouldForwardOnStop() {
        activityController.start().stop();

        verify(lightCycle).onStop(fragment);
    }

    @Test
    public void shouldForwardOnResume() {
        activityController.start().resume();
        verify(lightCycle).onResume(fragment);
    }

    @Test
    public void shouldForwardOnPause() {
        activityController.start().resume().pause();

        verify(lightCycle).onPause(fragment);
    }

    @Test
    public void shouldForwardOnViewCreated() {
        final Bundle savedInstanceState = new Bundle();
        View view = new View(Robolectric.application);
        fragment.onViewCreated(view, savedInstanceState);

        verify(lightCycle).onViewCreated(fragment, view, savedInstanceState);
    }

    @Test
    public void shouldForwardOnDestroyView() {
        activityController.destroy();

        verify(lightCycle).onDestroyView(fragment);
    }

    private static ActivityController<FragmentActivity> attach(Fragment fragment) {
        final ActivityController<FragmentActivity> fragmentActivityActivityController = Robolectric.buildActivity(FragmentActivity.class);
        FragmentActivity activity = fragmentActivityActivityController.create().get();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();

        return fragmentActivityActivityController;
    }
}