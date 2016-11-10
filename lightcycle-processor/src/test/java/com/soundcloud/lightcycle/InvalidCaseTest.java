package com.soundcloud.lightcycle;

import com.google.common.base.Joiner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class InvalidCaseTest {

    private static final String PRIVATE_FIELDS_TEST_FRAGMENT = Joiner.on("\n").join(
            "package com.test;",
            "",
            "import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;",
            "import com.soundcloud.lightcycle.LightCycle;",
            "import com.soundcloud.lightcycle.LightCycleDispatcher;",
            "import com.soundcloud.lightcycle.SupportFragmentLightCycle;",
            "",
            "import android.support.v4.app.Fragment;",
            "",
            "public class PrivateFieldsTestFragment extends Fragment implements LightCycleDispatcher<SupportFragmentLightCycle> {",
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
            "}");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenLightCycleFieldIsPrivate() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Annotated fields cannot be private: com.test.PrivateFieldsTestFragment#lightCycle1(com.test.LightCycle1)");

        JavaFileObject privateFieldsTestFragment = forSourceString("com/test/PrivateFieldsTestFragment", PRIVATE_FIELDS_TEST_FRAGMENT);

        assertAbout(javaSource())
                .that(privateFieldsTestFragment)
                .processedWith(new LightCycleProcessor())
                .compilesWithoutError();
    }

    private static final String FIELDS_NOT_LIGHTCYCLE_TEST_FRAGMENT = Joiner.on("\n").join(
            "package com.test;",
            "",
            "import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle;",
            "import com.soundcloud.lightcycle.LightCycle;",
            "import com.soundcloud.lightcycle.LightCycleDispatcher;",
            "import com.soundcloud.lightcycle.SupportFragmentLightCycle;",
            "",
            "import android.support.v4.app.Fragment;",
            "",
            "public class FieldsNotLightCyclesTestFragment extends Fragment implements LightCycleDispatcher<SupportFragmentLightCycle> {",
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
            "}");

    @Test
    public void lightCycleFieldIsNotALightCycle() {
        JavaFileObject privateFieldsTestFragment = forSourceString("com/test/FieldsNotLightCyclesTestFragment", FIELDS_NOT_LIGHTCYCLE_TEST_FRAGMENT);

        assertAbout(javaSource())
                .that(privateFieldsTestFragment)
                .processedWith(new LightCycleProcessor())
                .failsToCompile()
                .withErrorContaining("no suitable method found for lift(com.test.LightCycle1)");
    }

    private static final String MISSING_GENERIC_TEST_ACTIVITY = Joiner.on("\n").join(
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
            "}");

    @Test
    public void missingGenericTestActivity() {
        JavaFileObject missingGenericTestActivity = forSourceString("com/test/MissingGenericTestActivity", MISSING_GENERIC_TEST_ACTIVITY);

        assertAbout(javaSource())
                .that(missingGenericTestActivity)
                .processedWith(new LightCycleProcessor())
                .failsToCompile()
                .withErrorContaining("Expected 1 type argument but found 0. TypeArguments:[]. Did you forget to add generic types?");
    }

    private static final String DISPATCHER_NOT_FOUND_TEST_ACTIVITY = Joiner.on("\n").join(
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
            "}");

    @Test
    public void dispatcherNotFound() {
        JavaFileObject dispatcherNotFoundTestActivity = forSourceString("com/test/DispatcherNotFoundTestActivity", DISPATCHER_NOT_FOUND_TEST_ACTIVITY);

        assertAbout(javaSource())
                .that(dispatcherNotFoundTestActivity)
                .processedWith(new LightCycleProcessor())
                .failsToCompile()
                .withErrorContaining("Could not find dispatcher type. Did you forget to add the generic type?");
    }
}
