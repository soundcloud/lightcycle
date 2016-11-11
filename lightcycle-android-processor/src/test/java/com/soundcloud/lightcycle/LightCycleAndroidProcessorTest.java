package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;
import org.truth0.Truth;

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

    private static final String DIFFERENT_OBJECT_ACTIVITY_DISPATCHER_BASE_CLASS_SRC = Joiner.on("\n").join(
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
            "    public class LightCycle_BaseActivityAsObject<T extends String> extends BaseActivityAsObject {",
            "        private final ActivityLightCycleDispatcher<BaseActivityAsObject<T>> lightCycleDispatcher = new ActivityLightCycleDispatcher<BaseActivityAsObject<T>>();",
            "",
            "        @Override",
            "        public void bind(ActivityLightCycle<BaseActivityAsObject<T>> lightCycle) {",
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
            "        private BaseActivityAsObject<T> activity() {",
            "            return (BaseActivityAsObject<T>) this;",
            "        }",
            "    }");

    private static final String FRAGMENT_DISPATCHER_BASE_CLASS_SRC = Joiner.on("\n").join(
            "package test;",
            "",
            "import android.app.Activity;",
            "import android.app.Fragment;",
            "import android.os.Bundle;",
            "import android.view.MenuItem;",
            "import android.view.View;",
            "import com.soundcloud.lightcycle.FragmentLightCycle;",
            "import com.soundcloud.lightcycle.FragmentLightCycleDispatcher;",
            "import com.soundcloud.lightcycle.LightCycles;",
            "import java.lang.Override;",
            "import java.lang.SuppressWarnings;",
            "",
            "public class LightCycle_SimpleBaseFragment extends SimpleBaseFragment {",
            "private final FragmentLightCycleDispatcher<Fragment> lightCycleDispatcher = new " +
                    "FragmentLightCycleDispatcher<Fragment>();",
            "",
            "private boolean bound;",
            "",
            "@Override",
            "public void bind(FragmentLightCycle<Fragment> lightCycle) {",
            "    lightCycleDispatcher.bind(lightCycle);",
            "}",
            "",
            "@Override",
            "public void onAttach(Activity activity) {",
            "    super.onAttach(activity);",
            "    bindIfNecessary();",
            "    lightCycleDispatcher.onAttach(fragment(), activity);",
            "}",
            "",
            "@Override",
            "public void onViewCreated(View view, Bundle savedInstanceState) {",
            "    super.onViewCreated(view, savedInstanceState);",
            "    lightCycleDispatcher.onViewCreated(fragment(), view, savedInstanceState);",
            "}",
            "",
            "@Override",
            "public void onActivityCreated(Bundle savedInstanceState) {",
            "    super.onActivityCreated(savedInstanceState);",
            "    lightCycleDispatcher.onActivityCreated(fragment(), savedInstanceState);",
            "}",
            "",
            "@Override",
            "public void onCreate(Bundle savedInstanceState) {",
            "    super.onCreate(savedInstanceState);",
            "    lightCycleDispatcher.onCreate(fragment(), savedInstanceState);",
            "}",
            "",
            "@Override",
            "public void onStart() {",
            "    super.onStart();",
            "    lightCycleDispatcher.onStart(fragment());",
            "}",
            "",
            "@Override",
            "public void onResume() {",
            "    super.onResume();",
            "    lightCycleDispatcher.onResume(fragment());",
            "}",
            "",
            "@Override",
            "public boolean onOptionsItemSelected(MenuItem item) {",
            "    return lightCycleDispatcher.onOptionsItemSelected(fragment(), item) || super.onOptionsItemSelected(item);",
            "}",
            "",
            "@Override",
            "public void onPause() {",
            "    lightCycleDispatcher.onPause(fragment());",
            "    super.onPause();",
            "}",
            "",
            "@Override",
            "public void onStop() {",
            "    lightCycleDispatcher.onStop(fragment());",
            "    super.onStop();",
            "}",
            "",
            "@Override",
            "public void onSaveInstanceState(Bundle outState) {",
            "    super.onSaveInstanceState(outState);",
            "    lightCycleDispatcher.onSaveInstanceState(fragment(), outState);",
            "}",
            "",
            "@Override",
            "public void onDestroyView() {",
            "    lightCycleDispatcher.onDestroyView(fragment());",
            "    super.onDestroyView();",
            "}",
            "",
            "@Override",
            "public void onDestroy() {",
            "    lightCycleDispatcher.onDestroy(fragment());",
            "    super.onDestroy();",
            "}",
            "",
            "@Override",
            "public void onDetach() {",
            "    lightCycleDispatcher.onDetach(fragment());",
            "    super.onDetach();",
            "}",
            "",
            "@SuppressWarnings(\"unchecked\")",
            "private Fragment fragment() {",
            "    return (Fragment) this;",
            "}",
            "",
            "private void bindIfNecessary() {",
            "    if (!bound) {;",
            "        LightCycles.bind(this);",
            "        bound = true;",
            "    };",
            "}");

    @Test
    public void shouldGenerateSubclassForActivities() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("test/SimpleBaseActivity.java"))
                .processedWith(new LightCycleAndroidProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(forSourceString("test.LightCycle_SimpleBaseActivity",
                        ACTIVITY_DISPATCHER_BASE_CLASS_SRC));
    }

    @Test
    public void shouldGenerateSubclassForActivitiesWithNonStandardActivity() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("test/BaseActivityAsObject.java"))
                .processedWith(new LightCycleAndroidProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(forSourceString("test.LightCycle_BaseActivityAsObject",
                        DIFFERENT_OBJECT_ACTIVITY_DISPATCHER_BASE_CLASS_SRC));
    }

    @Test
    public void shouldGenerateSubclassForFragments() {
        Truth.ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("test/SimpleBaseFragment.java"))
                .processedWith(new LightCycleAndroidProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(forSourceString("test.LightCycle_SimpleBaseFragment",
                        FRAGMENT_DISPATCHER_BASE_CLASS_SRC));
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
                .withErrorContaining("test.NestedActivity.SimpleBaseActivity is nested. LightCycle generated base classes must be on a non-nested class.");
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
