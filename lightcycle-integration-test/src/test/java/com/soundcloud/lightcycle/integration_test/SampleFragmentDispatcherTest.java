package com.soundcloud.lightcycle.integration_test;

import static com.google.common.truth.Truth.assertThat;

import com.soundcloud.lightcycle.sample.basic.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;
import android.app.Fragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
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
