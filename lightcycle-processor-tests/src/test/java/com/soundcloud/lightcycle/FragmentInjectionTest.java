package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import javax.tools.JavaFileObject;

public class FragmentInjectionTest {

    @Test
    public void shouldGenerateInjectorForFragment() {
        JavaFileObject validTestFragment = JavaFileObjects.forSourceString("com/test/ValidTestFragment", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.FragmentLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.LightCycleDispatcher;",
                "import com.soundcloud.lightcycle.DefaultFragmentLightCycle;",
                "",
                "import android.app.Fragment;",
                "",
                "public class ValidTestFragment extends Fragment ",
                "        implements LightCycleDispatcher<FragmentLightCycle<ValidTestFragment>> {",
                "",
                "    @LightCycle LightCycle1 lightCycle1;",
                "    @LightCycle LightCycle2 lightCycle2;",
                "",
                "    @Override",
                "    public void bind(FragmentLightCycle<ValidTestFragment> lightCycle) {",
                "        // nop",
                "    }",
                "",
                "}",
                "",
                "class LightCycle1 extends DefaultFragmentLightCycle<Fragment> {",
                "}",
                "",
                "class LightCycle2 extends DefaultFragmentLightCycle<Fragment> {",
                "}"));

        JavaFileObject expectedSource = JavaFileObjects.forSourceString("com.test.ValidTestFragment$LightCycleBinder", Joiner.on("\n").join(
                "package com.test;",
                "",
                "public final class ValidTestFragment$LightCycleBinder {",
                "",
                "    public static void bind(ValidTestFragment target) {",
                "        final com.soundcloud.lightcycle.FragmentLightCycle<com.test.ValidTestFragment> lightCycle1$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
                "        target.bind(lightCycle1$Lifted);",
                "        final com.soundcloud.lightcycle.FragmentLightCycle<com.test.ValidTestFragment> lightCycle2$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
                "        target.bind(lightCycle2$Lifted);",
                "    }",
                "}"));

        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(validTestFragment)
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForLightCycleFragment() {
        JavaFileObject validTestLightCycleFragment = JavaFileObjects.forSourceString("com/test/ValidTestLightCycleFragment", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.DefaultFragmentLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.LightCycleDispatcher;",
                "import com.soundcloud.lightcycle.LightCycleFragment;",
                "",
                "import android.app.Activity;",
                "",
                "public class ValidTestLightCycleFragment ",
                "        extends LightCycleFragment<ValidTestLightCycleFragment> {",
                "    @LightCycle LightCycle1 lightCycle1;",
                "    @LightCycle LightCycle2 lightCycle2;",
                "",
                "}",
                "",
                "class LightCycle1 extends DefaultFragmentLightCycle<ValidTestLightCycleFragment> {",
                "}",
                "",
                "class LightCycle2 extends DefaultFragmentLightCycle<ValidTestLightCycleFragment> {",
                "}"));

        // Because neither the processor or the lib (java libraries) can depend on the api (Android library),
        // we have to create a fake `LightCycleFragment` here for testing purpose.
        JavaFileObject fakeLightCycleFragment = JavaFileObjects.forSourceString("com/soundcloud/lightcycle/LightCycleFragment", Joiner.on("\n").join(
                "package com.soundcloud.lightcycle;",
                "",
                "import android.app.Fragment;",
                "",
                "public abstract class LightCycleFragment<FragmentType extends Fragment>",
                "        extends Fragment",
                "        implements LightCycleDispatcher<FragmentLightCycle<FragmentType>> {",
                "",
                "    @Override",
                "    public void bind(FragmentLightCycle<FragmentType> lifeCycleComponent) { }",
                "",
                "}"));

        JavaFileObject expectedSource = JavaFileObjects.forSourceString("com.test.ValidTestLightCycleFragment$LightCycleBinder", Joiner.on("\n").join(
                "package com.test;",
                "",
                "public final class ValidTestLightCycleFragment$LightCycleBinder {",
                "",
                "    public static void bind(ValidTestLightCycleFragment target) {",
                "        final com.soundcloud.lightcycle.FragmentLightCycle<com.test.ValidTestLightCycleFragment> lightCycle1$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
                "        target.bind(lightCycle1$Lifted);",
                "        final com.soundcloud.lightcycle.FragmentLightCycle<com.test.ValidTestLightCycleFragment> lightCycle2$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
                "        target.bind(lightCycle2$Lifted);",
                "    }",
                "}"));

        Truth.assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(ImmutableList.of(validTestLightCycleFragment, fakeLightCycleFragment))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }
}
