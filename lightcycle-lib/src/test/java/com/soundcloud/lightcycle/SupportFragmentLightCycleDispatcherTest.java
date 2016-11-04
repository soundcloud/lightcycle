package com.soundcloud.lightcycle;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

@RunWith(MockitoJUnitRunner.class)
public class SupportFragmentLightCycleDispatcherTest {
    @Mock private SupportFragmentLightCycle<Fragment> lifeCycleComponent1;
    @Mock private SupportFragmentLightCycle<Fragment> lifeCycleComponent2;
    @Mock private Fragment fragment;
    @Mock private Activity activity;
    private SupportFragmentLightCycleDispatcher<Fragment> dispatcher;

    @Before
    public void setUp() throws Exception {
        dispatcher = new SupportFragmentLightCycleDispatcher<>();
        dispatcher.bind(lifeCycleComponent1);
        dispatcher.bind(lifeCycleComponent2);
    }

    @Test
    public void shouldNotifyOnAttach() {
        dispatcher.onAttach(fragment, activity);

        verify(lifeCycleComponent1).onAttach(fragment, activity);
        verify(lifeCycleComponent2).onAttach(fragment, activity);
    }

    @Test
    public void shouldNotifyOnCreate() {
        final Bundle bundle = Bundle.EMPTY;

        dispatcher.onCreate(fragment, bundle);

        verify(lifeCycleComponent1).onCreate(fragment, bundle);
        verify(lifeCycleComponent2).onCreate(fragment, bundle);
    }

    @Test
    public void shouldNotifyOnStart() {
        dispatcher.onStart(fragment);

        verify(lifeCycleComponent1).onStart(fragment);
        verify(lifeCycleComponent2).onStart(fragment);
    }

    @Test
    public void shouldNotifyOnResume() {
        dispatcher.onResume(fragment);

        verify(lifeCycleComponent1).onResume(fragment);
        verify(lifeCycleComponent2).onResume(fragment);
    }

    @Test
    public void shouldNotifyOnPause() {
        dispatcher.onPause(fragment);

        verify(lifeCycleComponent1).onPause(fragment);
        verify(lifeCycleComponent2).onPause(fragment);
    }

    @Test
    public void shouldNotifyOnStop() {
        dispatcher.onStop(fragment);

        verify(lifeCycleComponent1).onStop(fragment);
        verify(lifeCycleComponent2).onStop(fragment);
    }

    @Test
    public void shouldNotifyOnSaveInstanceState() {
        final Bundle bundle = Bundle.EMPTY;

        dispatcher.onSaveInstanceState(fragment, bundle);

        verify(lifeCycleComponent1).onSaveInstanceState(fragment, bundle);
        verify(lifeCycleComponent2).onSaveInstanceState(fragment, bundle);
    }

    @Test
    public void shouldNotifyOnActivityCreated() {
        final Bundle bundle = Bundle.EMPTY;

        dispatcher.onActivityCreated(fragment, bundle);

        verify(lifeCycleComponent1).onActivityCreated(fragment, bundle);
        verify(lifeCycleComponent2).onActivityCreated(fragment, bundle);
    }

    @Test
    public void shouldNotifyOnDestroy() {
        dispatcher.onDestroy(fragment);

        verify(lifeCycleComponent1).onDestroy(fragment);
        verify(lifeCycleComponent2).onDestroy(fragment);
    }

    @Test
    public void shouldNotifyOnDetach() {
        dispatcher.onDetach(fragment);

        verify(lifeCycleComponent1).onDetach(fragment);
        verify(lifeCycleComponent2).onDetach(fragment);
    }

    @Test
    public void shouldNotifyOnViewCreated() {
        final Bundle bundle = Bundle.EMPTY;
        final View view = mock(View.class);

        dispatcher.onViewCreated(fragment, view, bundle);

        verify(lifeCycleComponent1).onViewCreated(fragment, view, bundle);
        verify(lifeCycleComponent2).onViewCreated(fragment, view, bundle);
    }

    @Test
    public void shouldNotifyOnDestroyView() {
        dispatcher.onDestroyView(fragment);

        verify(lifeCycleComponent1).onDestroyView(fragment);
        verify(lifeCycleComponent2).onDestroyView(fragment);
    }

    @Test(expected = NullPointerException.class)
    public void nullBinderTarget() {
        dispatcher.bind(null);
    }
}
