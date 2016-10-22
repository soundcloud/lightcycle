package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;
import org.truth0.Truth;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class LightCycleProcessorTest {

    private static final String FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestFragment target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");

    private static final String LC_FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestLightCycleFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestLightCycleFragment target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");

    private static final String SUPPORT_FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestSupportFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestSupportFragment target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");

    private static final String LC_SUPPORT_DIALOG_FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestLightCycleSupportDialogFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestLightCycleSupportDialogFragment target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");


    private static final String LC_SUPPORT_FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestLightCycleSupportFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestLightCycleSupportFragment target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");


    private static final String ACTIVITY_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestActivity$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestActivity target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");

    private static final String ACTIVITY_HIERARCHY_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestHierarchyActivity$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestHierarchyActivity target) {",
            "        com.test.BaseActivity$LightCycleBinder.bind(target);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");

    private static final String LC_APPCOMPAT_ACTIVITY_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestLightCycleAppCompatActivity$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestLightCycleAppCompatActivity target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");

    private static final String LC_ACTION_BAR_ACTIVITY_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestLightCycleActionBarActivity$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestLightCycleActionBarActivity target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");

    private static final String LC_PARAMETERIZED_DISPATCHER_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "public final class ValidTestParameterizedDispatcher$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestParameterizedDispatcher target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
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

    @Test
    public void shouldGenerateInjectorForLightCycleAppCompatActivity() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestLightCycleAppCompatActivity$LightCycleBinder", LC_APPCOMPAT_ACTIVITY_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestLightCycleAppCompatActivity.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForLightCycleActionBarActivity() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestLightCycleActionBarActivity$LightCycleBinder", LC_ACTION_BAR_ACTIVITY_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestLightCycleActionBarActivity.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForLightCycleSupportFragment() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestLightCycleSupportFragment$LightCycleBinder", LC_SUPPORT_FRAGMENT_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestLightCycleSupportFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForLightCycleSupportDialogFragment() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestLightCycleSupportDialogFragment$LightCycleBinder", LC_SUPPORT_DIALOG_FRAGMENT_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestLightCycleSupportDialogFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForLightCycleFragment() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestLightCycleFragment$LightCycleBinder", LC_FRAGMENT_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestLightCycleFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForParameterizeDispatcher() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestParameterizedDispatcher$LightCycleBinder", LC_PARAMETERIZED_DISPATCHER_BINDER_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestParameterizedDispatcher.java"))
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

    @Test
    public void invalidTestActivity() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/InvalidTestActivity.java"))
                .processedWith(new LightCycleProcessor())
                .failsToCompile();
    }
}
