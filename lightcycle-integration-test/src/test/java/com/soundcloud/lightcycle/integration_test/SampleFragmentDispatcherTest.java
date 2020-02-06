package com.soundcloud.lightcycle.integration_test;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class SampleFragmentDispatcherTest {
    private Fragment fragment = new SampleFragment();
    private Activity activity = new SampleActivity();

    private SampleFragmentDispatcher dispatcher = new SampleFragmentDispatcher();

    @Test
    public void bindOnlyOnceWhenReAttached() {
        dispatcher.onAttach(fragment, activity);
        dispatcher.onAttach(fragment, activity);

        assertThat(dispatcher.bindCount).is(1);
    }
}
