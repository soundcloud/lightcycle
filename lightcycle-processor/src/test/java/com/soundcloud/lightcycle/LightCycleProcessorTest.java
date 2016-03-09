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
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");

    private static final String SUPPORT_FRAGMENT_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "",
            "public final class ValidTestSupportFragment$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestSupportFragment target) {",
            "        target.bind(target.lightCycle1);",
            "        target.bind(target.lightCycle2);",
            "    }",
            "}");

    private static final String ACTIVITY_BINDER_SRC = Joiner.on("\n").join(
            "package com.test;",
            "",
            "",
            "public final class ValidTestActivity$LightCycleBinder {",
            "",
            "    public static void bind(ValidTestActivity target) {",
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
