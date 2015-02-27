package com.soundcloud.android.lightcycle;

import org.jetbrains.annotations.Nullable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

public interface SupportFragmentLightCycle<T extends Fragment> {
    void onCreate(T fragment, @Nullable Bundle bundle);
    void onViewCreated(T fragment, View view, @Nullable Bundle savedInstanceState);
    void onStart(T fragment);
    void onResume(T fragment);
    boolean onOptionsItemSelected(T fragment, MenuItem item);
    void onPause(T fragment);
    void onStop(T fragment);
    void onSaveInstanceState(T fragment, Bundle bundle);
    void onRestoreInstanceState(T fragment, Bundle bundle);
    void onDestroyView(T fragment);
    void onDestroy(T fragment);
}
