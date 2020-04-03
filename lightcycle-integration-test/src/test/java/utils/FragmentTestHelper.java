package utils;

import org.robolectric.Robolectric;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

public abstract class FragmentTestHelper {
    public abstract FragmentTestHelper removeFragment();

    public abstract FragmentTestHelper addFragment();

    public static FragmentTestHelper from(Fragment fragment) {
        return SupportFragmentTestHelper.create(fragment);
    }

    private static final class SupportFragmentTestHelper extends FragmentTestHelper {

        private final FragmentManager fragmentManager;
        private final Fragment fragment;

        SupportFragmentTestHelper(FragmentManager fragmentManager, Fragment fragment) {
            this.fragmentManager = fragmentManager;
            this.fragment = fragment;
        }

        static SupportFragmentTestHelper create(Fragment fragment) {
            return new SupportFragmentTestHelper(
                    Robolectric.buildActivity(SupportFragmentControllerActivity.class)
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
