package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

/**
 * Created by yuwono-niko on 11/10/16.
 */

public class ParameterizedDispatcherTest {

    private static final String VALID_TEST_PARAMETERIZED_DISPATCHER = Joiner.on("\n").join(
            "package com.test;",
            "",
            "import com.soundcloud.lightcycle.FragmentLightCycle;",
            "import com.soundcloud.lightcycle.LightCycle;",
            "import com.soundcloud.lightcycle.FragmentLightCycleDispatcher;",
            "import com.soundcloud.lightcycle.DefaultFragmentLightCycle;",
            "",
            "import android.app.Fragment;",
            "",
            "public class ValidTestParameterizedDispatcher<SomeType> extends FragmentLightCycleDispatcher<Fragment>{",
            "",
            "    @LightCycle DefaultFragmentLightCycle<Fragment> lightCycle1;",
            "    @LightCycle DefaultFragmentLightCycle<Fragment> lightCycle2;",
            "",
            "}");

    // Because neither the processor or the lib (java libraries) can depend on the api (Android library),
    // we have to create a fake `LightCycleActionBarActivity` here for testing purpose.
    private static final String FAKE_FRAGMENT_LIGHTCYCLE_DISPATCHER = Joiner.on("\n").join(
            "package com.soundcloud.lightcycle;",
            "",
            "import android.annotation.TargetApi;",
            "import android.app.Activity;",
            "import android.app.Fragment;",
            "import android.content.Context;",
            "import android.os.Build;",
            "import android.os.Bundle;",
            "import android.view.MenuItem;",
            "import android.view.View;",
            "",
            "@TargetApi(Build.VERSION_CODES.HONEYCOMB)",
            "public class FragmentLightCycleDispatcher<T extends Fragment>",
            "        implements LightCycleDispatcher<FragmentLightCycle<T>>, FragmentLightCycle<T> {",
            "",
            "    public FragmentLightCycleDispatcher() {",
            "    }",
            "",
            "    @Override",
            "    public void bind(FragmentLightCycle<T> lightCycle) { }",
            "",
            "    @Override",
            "    public void onAttach(T fragment, Activity activity) { }",
            "",
            "    @Override",
            "    public void onAttach(T fragment, Context context) { }",
            "",
            "    @Override",
            "    public void onCreate(T fragment, Bundle bundle) { }",
            "",
            "    @Override",
            "    public void onViewCreated(T fragment, View view, Bundle savedInstanceState) { }",
            "",
            "    @Override",
            "    public void onActivityCreated(T fragment, Bundle bundle) { }",
            "",
            "    @Override",
            "    public void onStart(T fragment) { }",
            "",
            "    @Override",
            "    public void onResume(T fragment) { }",
            "",
            "    @Override",
            "    public boolean onOptionsItemSelected(T fragment, MenuItem item) {",
            "        return false;",
            "    }",
            "",
            "    @Override",
            "    public void onPause(T fragment) { }",
            "",
            "    @Override",
            "    public void onStop(T fragment) { }",
            "",
            "    @Override",
            "    public void onSaveInstanceState(T fragment, Bundle bundle) { }",
            "",
            "    @Override",
            "    public void onDestroyView(T fragment) { }",
            "",
            "    @Override",
            "    public void onDestroy(T fragment) { }",
            "",
            "    @Override",
            "    public void onDetach(T fragment) { }",
            "}");

    private static final String LC_PARAMETERIZED_DISPATCHER_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestParameterizedDispatcher$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestParameterizedDispatcher target) {",
            "        final com.soundcloud.lightcycle.FragmentLightCycle<android.app.Fragment> lightCycle1$Lifted = com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
            "        target.bind(lightCycle1$Lifted);",
            "        final com.soundcloud.lightcycle.FragmentLightCycle<android.app.Fragment> lightCycle2$Lifted = com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
            "        target.bind(lightCycle2$Lifted);",
            "    }",
            "}");

    @Test
    public void shouldGenerateInjectorForParameterizedDispatcher() {
        JavaFileObject validTestParameterizedDispatcher = forSourceString("com/test/ValidTestParameterizedDispatcher", VALID_TEST_PARAMETERIZED_DISPATCHER);
        JavaFileObject fakeFragmentLightCycleDispatcher = forSourceString("com/soundcloud/lightcycle/FragmentLightCycleDispatcher", FAKE_FRAGMENT_LIGHTCYCLE_DISPATCHER);
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestParameterizedDispatcher$LightCycleBinder", LC_PARAMETERIZED_DISPATCHER_BINDER_SRC);
        assertAbout(javaSources())
                .that(ImmutableList.of(validTestParameterizedDispatcher, fakeFragmentLightCycleDispatcher))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }
}
