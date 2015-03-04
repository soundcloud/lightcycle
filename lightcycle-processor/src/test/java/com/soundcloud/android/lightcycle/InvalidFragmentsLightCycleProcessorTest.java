package com.soundcloud.android.lightcycle;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.truth0.Truth;

@Ignore
public class InvalidFragmentsLightCycleProcessorTest {

    @Before
    public void dontPrintExceptions() {
        // get rid of the stack trace prints for expected exceptions
        //System.setErr(new PrintStream(new NullStream()));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenLightCycleFieldIsPrivate() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/PrivateFieldsTestFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError();
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenLightCycleFieldIsNotALightCycle() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("com/test/FieldsNotLightCyclesTestFragment.java"))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError();
    }
}
