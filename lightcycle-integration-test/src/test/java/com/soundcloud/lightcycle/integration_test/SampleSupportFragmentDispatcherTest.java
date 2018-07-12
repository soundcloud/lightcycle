package com.soundcloud.lightcycle.integration_test;

import static com.google.common.truth.Truth.assertThat;

import com.soundcloud.lightcycle.sample.basic.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class SampleSupportFragmentDispatcherTest {
    private SampleSupportFragment fragment = new SampleSupportFragment();
    private Activity activity = new SampleAppCompatActivity();

    private SampleSupportFragmentDispatcher dispatcher = new SampleSupportFragmentDispatcher();

    @Test
    public void bindOnlyOnceWhenReAttached() {
        dispatcher.onAttach(fragment, activity);
        dispatcher.onAttach(fragment, activity);

        assertThat(dispatcher.bindCount).is(1);
    }
}
