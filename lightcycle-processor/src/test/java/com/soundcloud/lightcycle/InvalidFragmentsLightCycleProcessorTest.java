package com.soundcloud.lightcycle;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Before;
import org.junit.Test;
import org.truth0.Truth;

public class InvalidFragmentsLightCycleProcessorTest {

    @Before
    public void dontPrintExceptions() {
        // get rid of the stack trace prints for expected exceptions
        //System.setErr(new PrintStream(new NullStream()));
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
