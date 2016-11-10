package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

/**
 * Created by yuwono-niko on 11/9/16.
 */

public class SupportFragmentInjectionTest {

    private static final String VALID_TEST_SUPPORT_FRAGMENT = Joiner.on("\n").join(
            "package com.test;",
            "",
            "import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;",
            "import com.soundcloud.lightcycle.LightCycle;",
            "import com.soundcloud.lightcycle.LightCycleDispatcher;",
            "import com.soundcloud.lightcycle.SupportFragmentLightCycle;",
            "",
            "import android.support.v4.app.Fragment;",
            "",
            "public class ValidTestSupportFragment extends Fragment implements LightCycleDispatcher<SupportFragmentLightCycle<ValidTestSupportFragment>> {",
            "",
            "    @LightCycle LightCycle1 lightCycle1;",
            "    @LightCycle LightCycle2 lightCycle2;",
            "",
            "    @Override",
            "    public void bind(SupportFragmentLightCycle<ValidTestSupportFragment> lightCycle) {",
            "        // nop",
            "    }",
            "",
            "}",
            "",
            "class LightCycle1 extends DefaultSupportFragmentLightCycle<ValidTestSupportFragment> {",
            "}",
            "",
            "class LightCycle2 extends DefaultSupportFragmentLightCycle<ValidTestSupportFragment> {",
            "}");

    private static final String SUPPORT_FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestSupportFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestSupportFragment target) {",
            "        final com.soundcloud.lightcycle.SupportFragmentLightCycle<com.test.ValidTestSupportFragment> lightCycle1$Lifted = com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
            "        target.bind(lightCycle1$Lifted);",
            "        final com.soundcloud.lightcycle.SupportFragmentLightCycle<com.test.ValidTestSupportFragment> lightCycle2$Lifted = com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
            "        target.bind(lightCycle2$Lifted);",
            "    }",
            "}");

    @Test
    public void shouldGenerateInjectorForSupportFragment() {
        JavaFileObject validTestSupportFragment = forSourceString("com/test/ValidTestSupportFragment", VALID_TEST_SUPPORT_FRAGMENT);
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestSupportFragment$LightCycleBinder", SUPPORT_FRAGMENT_BINDER_SRC);

        assertAbout(javaSource())
                .that(validTestSupportFragment)
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    private static final String VALID_TEST_LIGHTCYCLE_SUPPORT_FRAGMENT = Joiner.on("\n").join(
            "package com.test;",
            "",
            "import com.soundcloud.lightcycle.ActivityLightCycle;",
            "import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;",
            "import com.soundcloud.lightcycle.LightCycle;",
            "import com.soundcloud.lightcycle.LightCycleDispatcher;",
            "import com.soundcloud.lightcycle.LightCycleSupportFragment;",
            "",
            "import android.app.Activity;",
            "",
            "public class ValidTestLightCycleSupportFragment extends LightCycleSupportFragment<ValidTestLightCycleSupportFragment> {",
            "    @LightCycle LightCycle1 lightCycle1;",
            "    @LightCycle LightCycle2 lightCycle2;",
            "",
            "}",
            "",
            "class LightCycle1 extends DefaultSupportFragmentLightCycle<ValidTestLightCycleSupportFragment> {",
            "}",
            "",
            "class LightCycle2 extends DefaultSupportFragmentLightCycle<ValidTestLightCycleSupportFragment> {",
            "}");

    // Because neither the processor or the lib (java libraries) can depend on the api (Android library),
    // we have to create a fake `LightCycleSupportFragment` here for testing purpose.
    private static final String FAKE_LIGHTCYCLE_SUPPORT_FRAGMENT = Joiner.on("\n").join(
            "package com.soundcloud.lightcycle;",
            "",
            "import android.support.v4.app.Fragment;",
            "",
            "public abstract class LightCycleSupportFragment<FragmentType extends Fragment>",
            "        extends Fragment",
            "        implements LightCycleDispatcher<SupportFragmentLightCycle<FragmentType>> {",
            "",
            "    @Override",
            "    public void bind(SupportFragmentLightCycle<FragmentType> lifeCycleComponent) { }",
            "",
            "}");

    private static final String LC_SUPPORT_FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestLightCycleSupportFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestLightCycleSupportFragment target) {",
            "        final com.soundcloud.lightcycle.SupportFragmentLightCycle<com.test.ValidTestLightCycleSupportFragment> lightCycle1$Lifted = com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
            "        target.bind(lightCycle1$Lifted);",
            "        final com.soundcloud.lightcycle.SupportFragmentLightCycle<com.test.ValidTestLightCycleSupportFragment> lightCycle2$Lifted = com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
            "        target.bind(lightCycle2$Lifted);",
            "    }",
            "}");

    @Test
    public void shouldGenerateInjectorForLightCycleSupportFragment() {
        JavaFileObject validTestLightCycleSupportFragment = forSourceString("com/test/ValidTestLightCycleSupportFragment", VALID_TEST_LIGHTCYCLE_SUPPORT_FRAGMENT);
        JavaFileObject fakeLightCycleSupportFragment = forSourceString("com/soundcloud/lightcycle/LightCycleSupportFragment", FAKE_LIGHTCYCLE_SUPPORT_FRAGMENT);
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestLightCycleSupportFragment$LightCycleBinder", LC_SUPPORT_FRAGMENT_BINDER_SRC);
        assertAbout(javaSources())
                .that(ImmutableList.of(validTestLightCycleSupportFragment, fakeLightCycleSupportFragment))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    private static final String VALID_TEST_LIGHTCYCLE_SUPPORT_DIALOG_FRAGMENT = Joiner.on("\n").join(
            "package com.test;",
            "",
            "import com.soundcloud.lightcycle.ActivityLightCycle;",
            "import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;",
            "import com.soundcloud.lightcycle.LightCycle;",
            "import com.soundcloud.lightcycle.LightCycleDispatcher;",
            "import com.soundcloud.lightcycle.LightCycleSupportDialogFragment;",
            "",
            "import android.app.Activity;",
            "",
            "public class ValidTestLightCycleSupportDialogFragment extends LightCycleSupportDialogFragment<ValidTestLightCycleSupportDialogFragment> {",
            "    @LightCycle DialogLightCycle1 lightCycle1;",
            "    @LightCycle DialogLightCycle2 lightCycle2;",
            "",
            "}",
            "",
            "class DialogLightCycle1 extends DefaultSupportFragmentLightCycle<ValidTestLightCycleSupportDialogFragment> {",
            "}",
            "",
            "class DialogLightCycle2 extends DefaultSupportFragmentLightCycle<ValidTestLightCycleSupportDialogFragment> {",
            "}");

    // Because neither the processor or the lib (java libraries) can depend on the api (Android library),
    // we have to create a fake `LightCycleSupportDialogFragment` here for testing purpose.
    private static final String FAKE_LIGHTCYCLE_SUPPORT_DIALOG_FRAGMENT = Joiner.on("\n").join(
            "package com.soundcloud.lightcycle;",
            "",
            "import android.support.v4.app.DialogFragment;",
            "import android.support.v4.app.Fragment;",
            "",
            "public abstract class LightCycleSupportDialogFragment<FragmentType extends Fragment>",
            "        extends DialogFragment",
            "        implements LightCycleDispatcher<SupportFragmentLightCycle<FragmentType>> {",
            "",
            "    @Override",
            "    public void bind(SupportFragmentLightCycle<FragmentType> lifeCycleComponent) { }",
            "",
            "}");

    private static final String LC_SUPPORT_DIALOG_FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestLightCycleSupportDialogFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestLightCycleSupportDialogFragment target) {",
            "        final com.soundcloud.lightcycle.SupportFragmentLightCycle<com.test.ValidTestLightCycleSupportDialogFragment> lightCycle1$Lifted = com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
            "        target.bind(lightCycle1$Lifted);",
            "        final com.soundcloud.lightcycle.SupportFragmentLightCycle<com.test.ValidTestLightCycleSupportDialogFragment> lightCycle2$Lifted = com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
            "        target.bind(lightCycle2$Lifted);",
            "    }",
            "}");

    @Test
    public void shouldGenerateInjectorForLightCycleSupportDialogFragment() {
        JavaFileObject validTestLightCycleSupportDialogFragment = forSourceString("com/test/ValidTestLightCycleSupportDialogFragment", VALID_TEST_LIGHTCYCLE_SUPPORT_DIALOG_FRAGMENT);
        JavaFileObject fakeLightCycleSupportDialogFragment = forSourceString("com/soundcloud/lightcycle/LightCycleSupportDialogFragment", FAKE_LIGHTCYCLE_SUPPORT_DIALOG_FRAGMENT);
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestLightCycleSupportDialogFragment$LightCycleBinder", LC_SUPPORT_DIALOG_FRAGMENT_BINDER_SRC);
        assertAbout(javaSources())
                .that(ImmutableList.of(validTestLightCycleSupportDialogFragment, fakeLightCycleSupportDialogFragment))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }
}
