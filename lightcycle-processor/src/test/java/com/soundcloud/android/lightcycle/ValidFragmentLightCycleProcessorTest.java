package com.soundcloud.android.lightcycle;

import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
import org.truth0.Truth;

import javax.tools.JavaFileObject;

public class ValidFragmentLightCycleProcessorTest {

    private static final String INJECTOR = Joiner.on("\n").join(
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

    @Test
    public void shouldGenerateInjector() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestFragment$LightCycleBinder", INJECTOR);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

}
