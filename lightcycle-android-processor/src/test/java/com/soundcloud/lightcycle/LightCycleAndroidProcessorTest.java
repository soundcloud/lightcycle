package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;
import org.truth0.Truth;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class LightCycleAndroidProcessorTest {
    private static final String ACTIVITY_DISPATCHER_BASE_CLASS_SRC = Joiner.on("\n").join(
            "    package test;",
            "",
            "    import android.app.Activity;",
            "    import android.content.Intent;",
            "    import android.os.Bundle;",
            "    import android.view.MenuItem;",
            "    import com.soundcloud.lightcycle.ActivityLightCycle;",
            "    import com.soundcloud.lightcycle.ActivityLightCycleDispatcher;",
            "    import com.soundcloud.lightcycle.LightCycles;",
            "    import java.lang.Override;",
            "    import java.lang.SuppressWarnings;",
            "",
            "    public class LightCycle_SimpleBaseActivity extends SimpleBaseActivity {",
            "        private final ActivityLightCycleDispatcher<Activity> lightCycleDispatcher = new ActivityLightCycleDispatcher<Activity>();",
            "",
            "        @Override",
            "        public void bind(ActivityLightCycle<Activity> lightCycle) {",
            "            lightCycleDispatcher.bind(lightCycle);",
            "        }",
            "",
            "        @Override",
            "        protected void onCreate(Bundle savedInstanceState) {",
            "            super.onCreate(savedInstanceState);",
            "            LightCycles.bind(this);",
            "            lightCycleDispatcher.onCreate(activity(), savedInstanceState);",
            "        }",
            "",
            "        @Override",
            "        protected void onNewIntent(Intent intent) {",
            "            super.onNewIntent(intent);",
            "            lightCycleDispatcher.onNewIntent(activity(), intent);",
            "        }",
            "",
            "        @Override",
            "        protected void onStart() {",
            "            super.onStart();",
            "            lightCycleDispatcher.onStart(activity());",
            "        }",
            "",
            "        @Override",
            "        protected void onResume() {",
            "            super.onResume();",
            "            lightCycleDispatcher.onResume(activity());",
            "        }",
            "",
            "        @Override",
            "        public boolean onOptionsItemSelected(MenuItem item) {",
            "            return lightCycleDispatcher.onOptionsItemSelected(activity(), item) || super.onOptionsItemSelected(item);",
            "        }",
            "",
            "        @Override",
            "        protected void onPause() {",
            "            lightCycleDispatcher.onPause(activity());",
            "            super.onPause();",
            "        }",
            "",
            "        @Override",
            "        protected void onStop() {",
            "            lightCycleDispatcher.onStop(activity());",
            "            super.onStop();",
            "        }",
            "",
            "        @Override",
            "        protected void onSaveInstanceState(Bundle outState) {",
            "            super.onSaveInstanceState(outState);",
            "            lightCycleDispatcher.onSaveInstanceState(activity(), outState);",
            "        }",
            "",
            "        @Override",
            "        protected void onRestoreInstanceState(Bundle savedInstanceState) {",
            "            super.onRestoreInstanceState(savedInstanceState);",
            "            lightCycleDispatcher.onRestoreInstanceState(activity(), savedInstanceState);",
            "        }",
            "",
            "        @Override",
            "        protected void onDestroy() {",
            "            lightCycleDispatcher.onDestroy(activity());",
            "            super.onDestroy();",
            "        }",
            "",
            "        @SuppressWarnings(\"unchecked\")",
            "        private Activity activity() {",
            "            return (Activity) this;",
            "        }",
            "    }");

    @Test
    public void shouldGenerateSubclassForActivities() {
        JavaFileObject expectedSource = forSourceString("test.LightCycle_SimpleBaseActivity", ACTIVITY_DISPATCHER_BASE_CLASS_SRC);
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("test/SimpleBaseActivity.java"))
                .processedWith(new LightCycleAndroidProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(expectedSource);
    }

    @Test
    public void shouldThrowExceptionWhenAnnotationNotOnType() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("test/NoneTypeAnnotated.java"))
                .processedWith(new LightCycleAndroidProcessor())
                .failsToCompile()
                .withErrorContaining("annotation type not applicable to this kind of declaration");
    }

    @Test
    public void shouldThrowExceptionWhenAnnotationNotOnClass() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("test/BaseClassAnnotatedInterface.java"))
                .processedWith(new LightCycleAndroidProcessor())
                .failsToCompile()
                .withErrorContaining("test.BaseClassAnnotatedInterface is not a class.");
    }

    @Test
    public void shouldThrowExceptionWhenAnnotationOnNestedClass() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("test/NestedActivity.java"))
                .processedWith(new LightCycleAndroidProcessor())
                .failsToCompile()
                .withErrorContaining("test.NestedActivity.SimpleBaseActivity is nested. Annotation com.soundcloud.lightcycle.LightCycleBaseClass must be on a non-nested class.");
    }

    @Test
    public void shouldThrowExceptionWhenAnnotationOnFinalClass() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("test/FinalActivity.java"))
                .processedWith(new LightCycleAndroidProcessor())
                .failsToCompile()
                .withErrorContaining("test.FinalActivity is final. LightCycle generated base classes must be a subclass.");
    }
}
