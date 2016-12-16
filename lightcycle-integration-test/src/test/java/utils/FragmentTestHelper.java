package utils;

import org.robolectric.Robolectric;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;

public abstract class FragmentTestHelper {
    public abstract FragmentTestHelper removeFragment();

    public abstract FragmentTestHelper addFragment();

    public static FragmentTestHelper from(android.support.v4.app.Fragment fragment) {
        return SupportFragmentTestHelper.create(fragment);
    }

    public static FragmentTestHelper from(Fragment fragment) {
        return MyFragmentTestHelper.create(fragment);
    }

    private static final class SupportFragmentTestHelper extends FragmentTestHelper {

        private final android.support.v4.app.FragmentManager fragmentManager;
        private final android.support.v4.app.Fragment fragment;

        SupportFragmentTestHelper(FragmentManager fragmentManager, android.support.v4.app.Fragment fragment) {
            this.fragmentManager = fragmentManager;
            this.fragment = fragment;
        }

        static SupportFragmentTestHelper create(android.support.v4.app.Fragment fragment) {
            return new SupportFragmentTestHelper(Robolectric.buildActivity(SupportFragmentControllerActivity.class)
                                                            .create()
                                                            .get()
                                                            .getSupportFragmentManager(),
                                                 fragment);
        }

        @Override
        public FragmentTestHelper removeFragment() {
            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
            return this;
        }

        @Override
        public FragmentTestHelper addFragment() {
            fragmentManager
                    .beginTransaction().add(1, fragment)
                    .commit();
            return this;
        }

        static class SupportFragmentControllerActivity extends FragmentActivity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                LinearLayout view = new LinearLayout(this);
                view.setId(1);

                setContentView(view);
            }
        }
    }

    private static final class MyFragmentTestHelper extends FragmentTestHelper {

        private final android.app.FragmentManager fragmentManager;
        private final android.app.Fragment fragment;

        MyFragmentTestHelper(android.app.FragmentManager fragmentManager, Fragment fragment) {
            this.fragmentManager = fragmentManager;
            this.fragment = fragment;
        }

        static MyFragmentTestHelper create(android.app.Fragment fragment) {
            return new MyFragmentTestHelper(Robolectric.buildActivity(FragmentControllerActivity.class)
                                                       .create()
                                                       .get()
                                                       .getFragmentManager(),
                                            fragment);
        }

        @Override
        public FragmentTestHelper removeFragment() {
            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
            return this;
        }

        @Override
        public FragmentTestHelper addFragment() {
            fragmentManager
                    .beginTransaction().add(1, fragment)
                    .commit();
            return this;
        }

        public static class FragmentControllerActivity extends Activity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                LinearLayout view = new LinearLayout(this);
                view.setId(1);

                setContentView(view);
            }
        }
    }
}
