package com.soundcloud.lightcycle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public interface FragmentLightCycle<T> {
    void onAttach(T host, Activity activity);
    void onAttach(T host, Context context);
    void onCreate(T host, Bundle bundle);
    void onViewCreated(T host, View view, Bundle savedInstanceState);
    void onActivityCreated(T host, Bundle bundle);
    void onStart(T host);
    void onResume(T host);
    boolean onOptionsItemSelected(T host, MenuItem item);
    void onPause(T host);
    void onStop(T host);
    void onSaveInstanceState(T host, Bundle bundle);
    void onDestroyView(T host);
    void onDestroy(T host);
    void onDetach(T host);
}
