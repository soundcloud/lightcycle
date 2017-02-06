package com.soundcloud.lightcycle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import android.app.Activity;
import android.app.Fragment;

public class LightCyclesTest {

    @Test
    public void liftedActivityLightCycleEqualsContract() {
        EqualsVerifier.forClass(LightCycles.LiftedActivityLightCycle.class).verify();
    }

    @Test
    public void liftedFragmentLightCycleEqualsContract() {
        EqualsVerifier.forClass(LightCycles.LiftedFragmentLightCycle.class).verify();
    }

    @Test
    public void liftedSupportFragmentLightCycleEqualsContract() {
        EqualsVerifier.forClass(LightCycles.LiftedSupportFragmentLightCycle.class).verify();
    }

    @Test
    public void liftedActivityLightCycleAreEqualsWhenTheSourceIsTheSame() {
        final DefaultActivityLightCycle<Activity> lightCycle = new DefaultActivityLightCycle<>();
        final ActivityLightCycle<Activity> lift1 = LightCycles.lift(lightCycle);
        final ActivityLightCycle<Activity> lift2 = LightCycles.lift(lightCycle);

        assertThat(lift1, not(sameInstance(lift2)));
        assertThat(lift1, equalTo(lift2));
    }

    @Test
    public void liftedFragmentLightCycleAreEqualsWhenTheSourceIsTheSame() {
        final DefaultFragmentLightCycle<Fragment> lightCycle = new DefaultFragmentLightCycle<>();
        final FragmentLightCycle<Fragment> lift1 = LightCycles.lift(lightCycle);
        final FragmentLightCycle<Fragment> lift2 = LightCycles.lift(lightCycle);

        assertThat(lift1, not(sameInstance(lift2)));
        assertThat(lift1, equalTo(lift2));
    }

    @Test
    public void liftedSupportFragmentLightCycleAreEqualsWhenTheSourceIsTheSame() {
        final DefaultSupportFragmentLightCycle<android.support.v4.app.Fragment> lightCycle = new DefaultSupportFragmentLightCycle<>();
        final SupportFragmentLightCycle<android.support.v4.app.Fragment> lift1 = LightCycles.lift(lightCycle);
        final SupportFragmentLightCycle<android.support.v4.app.Fragment> lift2 = LightCycles.lift(lightCycle);

        assertThat(lift1, not(sameInstance(lift2)));
        assertThat(lift1, equalTo(lift2));
    }

}
