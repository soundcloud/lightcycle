package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import javax.tools.JavaFileObject;

public class ParameterizedDispatcherTest {

    @Test
    public void shouldGenerateInjectorForParameterizedDispatcher() {
        JavaFileObject validTestParameterizedDispatcher = JavaFileObjects.forSourceString("com/test/ValidTestParameterizedDispatcher", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.FragmentLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.FragmentLightCycleDispatcher;",
                "import com.soundcloud.lightcycle.DefaultFragmentLightCycle;",
                "",
                "import android.app.Fragment;",
                "",
                "public class ValidTestParameterizedDispatcher<SomeType> ",
                "        extends FragmentLightCycleDispatcher<Fragment>{",
                "",
                "    @LightCycle DefaultFragmentLightCycle<Fragment> lightCycle1;",
                "    @LightCycle DefaultFragmentLightCycle<Fragment> lightCycle2;",
                "",
                "}"));

        // Because neither the processor or the lib (java libraries) can depend on the api (Android library),
        // we have to create a fake `LightCycleActionBarActivity` here for testing purpose.
        JavaFileObject fakeFragmentLightCycleDispatcher = JavaFileObjects.forSourceString("com/soundcloud/lightcycle/FragmentLightCycleDispatcher", Joiner.on("\n").join(
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
                "}"));

        JavaFileObject expectedSource = JavaFileObjects.forSourceString("com.test.ValidTestParameterizedDispatcher$LightCycleBinder", Joiner.on("\n").join(
                "package com.test;",
                "",
                "public final class ValidTestParameterizedDispatcher$LightCycleBinder {",
                "",
                "    public static void bind(ValidTestParameterizedDispatcher target) {",
                "        final com.soundcloud.lightcycle.FragmentLightCycle<android.app.Fragment> lightCycle1$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
                "        target.bind(lightCycle1$Lifted);",
                "        final com.soundcloud.lightcycle.FragmentLightCycle<android.app.Fragment> lightCycle2$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
                "        target.bind(lightCycle2$Lifted);",
                "    }",
                "}"));

        Truth.assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(ImmutableList.of(validTestParameterizedDispatcher, fakeFragmentLightCycleDispatcher))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }
}
