package com.soundcloud.lightcycle;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public interface FragmentLightCycle<T extends Fragment> {
    void onAttach(T fragment, Activity activity);
    void onCreate(T fragment, Bundle bundle);
    void onViewCreated(T fragment, View view, Bundle savedInstanceState);
    void onActivityCreated(T fragment, Bundle bundle);
    void onStart(T fragment);
    void onResume(T fragment);
    boolean onOptionsItemSelected(T fragment, MenuItem item);
    void onPause(T fragment);
    void onStop(T fragment);
    void onSaveInstanceState(T fragment, Bundle bundle);
    void onDestroyView(T fragment);
    void onDestroy(T fragment);
    void onDetach(T fragment);
}
