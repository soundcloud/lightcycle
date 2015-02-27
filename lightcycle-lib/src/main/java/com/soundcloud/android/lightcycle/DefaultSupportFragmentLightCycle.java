package com.soundcloud.android.lightcycle;

import org.jetbrains.annotations.Nullable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

public class DefaultSupportFragmentLightCycle<T extends Fragment> implements SupportFragmentLightCycle<T> {
    @Override
    public void onCreate(T fragment, @Nullable Bundle bundle) { /* no-op */ }

    @Override
    public void onViewCreated(T fragment, View view, @Nullable Bundle savedInstanceState) { /* no-op */ }

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
}
