package com.soundcloud.android.lightcycle;

import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Ignore;
import org.junit.Test;
import org.truth0.Truth;

import javax.tools.JavaFileObject;

@Ignore
public class ValidFragmentLightCycleProcessorTest {

    private static final String INJECTOR = Joiner.on("\n").join(
            "package com.test;",
            "",
            "import com.soundcloud.android.lightcycle.LightCycleSupportFragment;",
            "",
            "final class ValidTestFragment$LightCycleInjector",
            "        extends com.soundcloud.android.lightcycle.LightCycleInjector {",
            "",
            "    @Override",
            "    public void inject(LightCycleSupportFragment fragment) {",
            "        fragment.addLifeCycleComponent(((ValidTestFragment) fragment).lightCycle1);",
            "        fragment.addLifeCycleComponent(((ValidTestFragment) fragment).lightCycle2);",
            "    }",
            "}");

    @Test
    public void shouldGenerateInjector() {
        JavaFileObject expectedSource = forSourceString("com.test.ValidTestFragment$LightCycleInjector", INJECTOR);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/ValidTestFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

}
