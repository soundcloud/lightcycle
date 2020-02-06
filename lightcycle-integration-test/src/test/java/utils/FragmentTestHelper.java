package utils;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import org.robolectric.Robolectric;

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
}
