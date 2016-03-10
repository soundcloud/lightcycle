package com.soundcloud.lightcycle;

import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
import org.truth0.Truth;

import javax.tools.JavaFileObject;

public class LightCycleProcessorTest {

    private static final String FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "",
            "public final class ValidTestFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestFragment target) {",
            "        final com.soundcloud.lightcycle.FragmentLightCycle<com.test.ValidTestFragment> lightCycle1$Lifted = com.soundcloud.lightcycle.LightCycleBinder.lift(target.lightCycle1);",
            "        target.bind(lightCycle1$Lifted);",
            "        final com.soundcloud.lightcycle.FragmentLightCycle<com.test.ValidTestFragment> lightCycle2$Lifted = com.soundcloud.lightcycle.LightCycleBinder.lift(target.lightCycle2);",
            "        target.bind(lightCycle2$Lifted);",
            "    }",
            "}");

    private static final String SUPPORT_FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "",
            "public final class ValidTestSupportFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestSupportFragment target) {",
            "        final com.soundcloud.lightcycle.SupportFragmentLightCycle<com.test.ValidTestSupportFragment> lightCycle1$Lifted = com.soundcloud.lightcycle.LightCycleBinder.lift(target.lightCycle1);",
            "        target.bind(lightCycle1$Lifted);",
            "        final com.soundcloud.lightcycle.SupportFragmentLightCycle<com.test.ValidTestSupportFragment> lightCycle2$Lifted = com.soundcloud.lightcycle.LightCycleBinder.lift(target.lightCycle2);",
            "        target.bind(lightCycle2$Lifted);",
            "    }",
            "}");

    private static final String ACTIVITY_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "",
            "public final class ValidTestActivity$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestActivity target) {",
            "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.ValidTestActivity> lightCycle1$Lifted = com.soundcloud.lightcycle.LightCycleBinder.lift(target.lightCycle1);",
            "        target.bind(lightCycle1$Lifted);",
            "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.ValidTestActivity> lightCycle2$Lifted = com.soundcloud.lightcycle.LightCycleBinder.lift(target.lightCycle2);",
            "        target.bind(lightCycle2$Lifted);",
            "    }",
            "}");

    private static final String ACTIVITY_HIERARCHY_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "",
            "public final class ValidTestHierarchyActivity$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestHierarchyActivity target) {",
            "        com.test.BaseActivity$LightCycleBinder.bind(target);",
            "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.BaseActivity> lightCycle2$Lifted = com.soundcloud.lightcycle.LightCycleBinder.lift(target.lightCycle2);",
            "        target.bind(lightCycle2$Lifted);",
            "    }",
            "}");

    @Test
    public void shouldGenerateInjectorForFragment() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestFragment$LightCycleBinder", FRAGMENT_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForSupportFragment() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestSupportFragment$LightCycleBinder", SUPPORT_FRAGMENT_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestSupportFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForActivity() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestActivity$LightCycleBinder", ACTIVITY_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestActivity.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForActivityHierarchy() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestHierarchyActivity$LightCycleBinder", ACTIVITY_HIERARCHY_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestHierarchyActivity.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    //TODO: make this test more specific with a TestRule to catch false positives
    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenLightCycleFieldIsPrivate() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/PrivateFieldsTestFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError();
    }

    @Test
    public void shouldThrowExceptionWhenLightCycleFieldIsNotALightCycle() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/FieldsNotLightCyclesTestFragment.java"))
                .processedWith(new LightCycleProcessor())
                .failsToCompile();
    }

}
