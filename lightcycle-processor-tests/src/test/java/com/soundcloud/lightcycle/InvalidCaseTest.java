package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;
import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.tools.JavaFileObject;

public class InvalidCaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenLightCycleFieldIsPrivate() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Annotated fields cannot be private: "
                + "com.test.PrivateFieldsTestFragment#lightCycle1(com.test.LightCycle1)");

        JavaFileObject privateFieldsTestFragment = JavaFileObjects.forSourceString("com/test/PrivateFieldsTestFragment", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.LightCycleDispatcher;",
                "import com.soundcloud.lightcycle.SupportFragmentLightCycle;",
                "",
                "import androidx.fragment.app.Fragment;",
                "",
                "public class PrivateFieldsTestFragment extends Fragment ",
                "        implements LightCycleDispatcher<SupportFragmentLightCycle> {",
                "",
                "    private @LightCycle LightCycle1 lightCycle1;",
                "    private @LightCycle LightCycle2 lightCycle2;",
                "",
                "    @Override",
                "    public void bind(SupportFragmentLightCycle lightCycle) {",
                "        // nop",
                "    }",
                "",
                "}",
                "",
                "class LightCycle1 extends DefaultSupportFragmentLightCycle {",
                "}",
                "",
                "class LightCycle2 extends DefaultSupportFragmentLightCycle {",
                "}"));

        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(privateFieldsTestFragment)
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError();
    }

    @Test
    public void lightCycleFieldIsNotALightCycle() {
        JavaFileObject privateFieldsTestFragment = JavaFileObjects.forSourceString("com/test/FieldsNotLightCyclesTestFragment", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.LightCycleDispatcher;",
                "import com.soundcloud.lightcycle.SupportFragmentLightCycle;",
                "",
                "import android.app.Fragment;",
                "",
                "public class FieldsNotLightCyclesTestFragment extends Fragment ",
                "        implements LightCycleDispatcher<SupportFragmentLightCycle> {",
                "",
                "    @LightCycle LightCycle1 lightCycle1;",
                "    @LightCycle LightCycle2 lightCycle2;",
                "",
                "    @Override",
                "    public void bind(SupportFragmentLightCycle lightCycle) {",
                "        // nop",
                "    }",
                "",
                "}",
                "",
                "class LightCycle1 {",
                "}",
                "",
                "class LightCycle2 {",
                "}"));

        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(privateFieldsTestFragment)
                .processedWith(new LightCycleProcessor())
                .failsToCompile()
                .withErrorContaining("no suitable method found for lift(com.test.LightCycle1)");
    }

    @Test
    public void missingGenericTestActivity() {
        JavaFileObject missingGenericTestActivity = JavaFileObjects.forSourceString("com/test/MissingGenericTestActivity", Joiner.on("\n").join(
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
                "public class MissingGenericTestActivity extends LightCycleActionBarActivity {",
                "",
                "    @LightCycle LightCycle1 lightCycle1;",
                "    @LightCycle LightCycle2 lightCycle2;",
                "",
                "    @Override",
                "    public void bind(ActivityLightCycle lightCycle) {",
                "        // nop",
                "    }",
                "",
                "}",
                "",
                "class LightCycle1 extends DefaultActivityLightCycle<MissingGenericTestActivity> {",
                "}",
                "",
                "class LightCycle2 extends DefaultActivityLightCycle<MissingGenericTestActivity> {",
                "}"));

        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(missingGenericTestActivity)
                .processedWith(new LightCycleProcessor())
                .failsToCompile()
                .withErrorContaining("Expected 1 type argument but found 0. TypeArguments:[]. "
                        + "Did you forget to add generic types?");
    }

    @Test
    public void dispatcherNotFound() {
        JavaFileObject dispatcherNotFoundTestActivity = JavaFileObjects.forSourceString("com/test/DispatcherNotFoundTestActivity", Joiner.on("\n").join(
                "package com.test;",
                "",
                "import com.soundcloud.lightcycle.ActivityLightCycle;",
                "import com.soundcloud.lightcycle.DefaultActivityLightCycle;",
                "import com.soundcloud.lightcycle.LightCycle;",
                "import com.soundcloud.lightcycle.LightCycleDispatcher;",
                "",
                "import android.app.Activity;",
                "",
                "public class DispatcherNotFoundTestActivity extends Activity {",
                "",
                "    @LightCycle LightCycle1 lightCycle1;",
                "    @LightCycle LightCycle2 lightCycle2;",
                "",
                "}",
                "",
                "class LightCycle1 extends DefaultActivityLightCycle<InvalidTestActivity> {",
                "}",
                "",
                "class LightCycle2 extends DefaultActivityLightCycle<InvalidTestActivity> {",
                "}"));

        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(dispatcherNotFoundTestActivity)
                .processedWith(new LightCycleProcessor())
                .failsToCompile()
                .withErrorContaining("Could not find dispatcher type. "
                        + "Did you forget to add the generic type?");
    }
}
