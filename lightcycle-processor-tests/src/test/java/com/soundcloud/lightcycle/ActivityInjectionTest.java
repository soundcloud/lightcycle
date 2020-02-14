package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import javax.tools.JavaFileObject;

public class ActivityInjectionTest {

    @Test
    public void shouldGenerateInjectorForActivity() {
        JavaFileObject validTestActivity = JavaFileObjects.forSourceString("com/test/ValidTestActivity", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.ActivityLightCycle;",
                "import com.soundcloud.lightcycle.DefaultActivityLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.LightCycleDispatcher;",
                "",
                "import android.app.Activity;",
                "",
                "public class ValidTestActivity ",
                "        extends Activity ",
                "        implements LightCycleDispatcher<ActivityLightCycle<ValidTestActivity>> {",
                "",
                "    @LightCycle LightCycle1 lightCycle1;",
                "    @LightCycle LightCycle2 lightCycle2;",
                "",
                "    @Override",
                "    public void bind(ActivityLightCycle<ValidTestActivity> lightCycle) {",
                "        // nop",
                "    }",
                "",
                "}",
                "",
                "class LightCycle1 extends DefaultActivityLightCycle<ValidTestActivity> {",
                "}",
                "",
                "class LightCycle2 extends DefaultActivityLightCycle<ValidTestActivity> {",
                "}"));

        JavaFileObject expectedSource = JavaFileObjects.forSourceString("com.test.ValidTestActivity$LightCycleBinder", Joiner.on("\n").join(
                "package com.test;",
                "",
                "public final class ValidTestActivity$LightCycleBinder {",
                "",
                "    public static void bind(ValidTestActivity target) {",
                "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.ValidTestActivity> lightCycle1$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
                "        target.bind(lightCycle1$Lifted);",
                "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.ValidTestActivity> lightCycle2$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
                "        target.bind(lightCycle2$Lifted);",
                "    }",
                "}"));

        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(validTestActivity)
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForActivityHierarchy() {
        JavaFileObject validTestHierarchyActivity = JavaFileObjects.forSourceString("com/test/ValidTestHierarchyActivity", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.ActivityLightCycle;",
                "import com.soundcloud.lightcycle.DefaultActivityLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.LightCycleDispatcher;",
                "",
                "import android.app.Activity;",
                "",
                "public class ValidTestHierarchyActivity  extends BaseActivity {",
                "    @LightCycle LightCycle2 lightCycle2;",
                "",
                "}",
                "",
                "class BaseActivity ",
                "        extends Activity ",
                "        implements LightCycleDispatcher<ActivityLightCycle<BaseActivity>> {",
                "",
                "    @LightCycle LightCycle1 lightCycle1;",
                "",
                "    @Override",
                "    public void bind(ActivityLightCycle<BaseActivity> lightCycle) {",
                "        // nop",
                "    }",
                "",
                "}",
                "",
                "class LightCycle1 extends DefaultActivityLightCycle<BaseActivity> {",
                "}",
                "",
                "class LightCycle2 extends DefaultActivityLightCycle<BaseActivity> {",
                "}"));

        JavaFileObject expectedSource = JavaFileObjects.forSourceString("com.test.ValidTestHierarchyActivity$LightCycleBinder", Joiner.on("\n").join(
                "package com.test;",
                "",
                "public final class ValidTestHierarchyActivity$LightCycleBinder {",
                "",
                "    public static void bind(ValidTestHierarchyActivity target) {",
                "        com.test.BaseActivity$LightCycleBinder.bind(target);",
                "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.BaseActivity> lightCycle2$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
                "        target.bind(lightCycle2$Lifted);",
                "    }",
                "}"));

        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(validTestHierarchyActivity)
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForLightCycleAppCompatActivity() {
        JavaFileObject validTestLightCycleAppCompatActivity = JavaFileObjects.forSourceString("com/test/ValidTestLightCycleAppCompatActivity", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.ActivityLightCycle;",
                "import com.soundcloud.lightcycle.DefaultActivityLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.LightCycleDispatcher;",
                "import com.soundcloud.lightcycle.LightCycleAppCompatActivity;",
                "",
                "import android.app.Activity;",
                "",
                "public class ValidTestLightCycleAppCompatActivity ",
                "        extends LightCycleAppCompatActivity<ValidTestLightCycleAppCompatActivity> {",
                "    @LightCycle LightCycle1 lightCycle1;",
                "    @LightCycle LightCycle2 lightCycle2;",
                "",
                "}",
                "",
                "class LightCycle1 extends DefaultActivityLightCycle<ValidTestLightCycleAppCompatActivity> {",
                "}",
                "",
                "class LightCycle2 extends DefaultActivityLightCycle<ValidTestLightCycleAppCompatActivity> {",
                "}"));

        // Because neither the processor or the lib (java libraries) can depend on the api (Android library),
        // we have to create a fake `LightCycleAppCompatActivity` here for testing purpose.
        JavaFileObject fakeLightCycleAppCompatActivity = JavaFileObjects.forSourceString("com/soundcloud/lightcycle/LightCycleAppCompatActivity", Joiner.on("\n").join(
                "package com.soundcloud.lightcycle;",
                "",
                "import android.app.Activity;",
                "",
                "public abstract class LightCycleAppCompatActivity<T extends Activity>",
                "        extends Activity",
                "        implements LightCycleDispatcher<ActivityLightCycle<T>> {",
                "",
                "    @Override",
                "    public void bind(ActivityLightCycle<T> lightCycle) { }",
                "",
                "}"));

        JavaFileObject expectedSource = JavaFileObjects.forSourceString("com.test.ValidTestLightCycleAppCompatActivity$LightCycleBinder", Joiner.on("\n").join(
                "package com.test;",
                "",
                "public final class ValidTestLightCycleAppCompatActivity$LightCycleBinder {",
                "",
                "    public static void bind(ValidTestLightCycleAppCompatActivity target) {",
                "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.ValidTestLightCycleAppCompatActivity> lightCycle1$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
                "        target.bind(lightCycle1$Lifted);",
                "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.ValidTestLightCycleAppCompatActivity> lightCycle2$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
                "        target.bind(lightCycle2$Lifted);",
                "    }",
                "}"));

        Truth.assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(ImmutableList.of(validTestLightCycleAppCompatActivity, fakeLightCycleAppCompatActivity))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }

    @Test
    public void shouldGenerateInjectorForLightCycleActionBarActivity() {
        JavaFileObject validTestLightCycleActionBarActivity = JavaFileObjects.forSourceString("com/test/ValidTestLightCycleActionBarActivity", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.ActivityLightCycle;",
                "import com.soundcloud.lightcycle.DefaultActivityLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.LightCycleDispatcher;",
                "import com.soundcloud.lightcycle.LightCycleActionBarActivity;",
                "",
                "import android.app.Activity;",
                "",
                "public class ValidTestLightCycleActionBarActivity ",
                "        extends LightCycleActionBarActivity<ValidTestLightCycleActionBarActivity> {",
                "    @LightCycle LightCycle1 lightCycle1;",
                "    @LightCycle LightCycle2 lightCycle2;",
                "",
                "}",
                "",
                "class LightCycle1 extends DefaultActivityLightCycle<ValidTestLightCycleActionBarActivity> {",
                "}",
                "",
                "class LightCycle2 extends DefaultActivityLightCycle<ValidTestLightCycleActionBarActivity> {",
                "}"));

        // Because neither the processor or the lib (java libraries) can depend on the api (Android library),
        // we have to create a fake `LightCycleActionBarActivity` here for testing purpose.
        JavaFileObject fakeLightCycleActionBarActivity = JavaFileObjects.forSourceString("com/soundcloud/lightcycle/LightCycleActionBarActivity", Joiner.on("\n").join(
                "package com.soundcloud.lightcycle;",
                "",
                "import android.app.Activity;",
                "",
                "public abstract class LightCycleActionBarActivity<T extends Activity>",
                "        extends Activity",
                "        implements LightCycleDispatcher<ActivityLightCycle<T>> {",
                "",
                "    @Override",
                "    public void bind(ActivityLightCycle<T> lightCycle) { }",
                "",
                "}"));

        JavaFileObject expectedSource = JavaFileObjects.forSourceString("com.test.ValidTestLightCycleActionBarActivity$LightCycleBinder", Joiner.on("\n").join(
                "package com.test;",
                "",
                "public final class ValidTestLightCycleActionBarActivity$LightCycleBinder {",
                "",
                "    public static void bind(ValidTestLightCycleActionBarActivity target) {",
                "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.ValidTestLightCycleActionBarActivity> lightCycle1$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle1);",
                "        target.bind(lightCycle1$Lifted);",
                "        final com.soundcloud.lightcycle.ActivityLightCycle<com.test.ValidTestLightCycleActionBarActivity> lightCycle2$Lifted = ",
                "                com.soundcloud.lightcycle.LightCycles.lift(target.lightCycle2);",
                "        target.bind(lightCycle2$Lifted);",
                "    }",
                "}"));

        Truth.assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(ImmutableList.of(validTestLightCycleActionBarActivity, fakeLightCycleActionBarActivity))
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError()
                .and().generatesSources(expectedSource);
    }
}
