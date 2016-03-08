package com.soundcloud.lightcycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DefaultFragmentLightCycle<T extends Fragment> implements FragmentLightCycle<T> {
    @Override
    public void onAttach(T fragment, Activity activity) { /* no-op */ }

    @Override
    public void onCreate(T fragment, Bundle bundle) { /* no-op */ }

    @Override
    public void onViewCreated(T fragment, View view, Bundle savedInstanceState) { /* no-op */ }

    @Override
    public void onStart(T fragment) { /* no-op */ }

    @Override
    public void onResume(T fragment) { /* no-op */ }

    @Override
    public boolean onOptionsItemSelected(T fragment, MenuItem item) {
        return false;
    }

    @Override
    public void onPause(T fragment) { /* no-op */ }

    @Override
    public void onStop(T fragment) { /* no-op */ }

    @Override
    public void onSaveInstanceState(T fragment, Bundle bundle) { /* no-op */ }

    @Override
    public void onRestoreInstanceState(T fragment, Bundle bundle) { /* no-op */ }

    @Override
    public void onDestroyView(T fragment) { /* no-op */ }

    @Override
    public void onDestroy(T fragment) { /* no-op */ }

    @Override
    public void onDetach(T fragment) { /* no-op */ }
}
