package com.soundcloud.lightcycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public interface SupportFragmentLightCycle<HostType> {
    void onAttach(HostType host, Activity activity);
    void onCreate(HostType host, Bundle bundle);
    void onViewCreated(HostType host, View view, Bundle savedInstanceState);
    void onActivityCreated(HostType host, Bundle bundle);
    void onStart(HostType host);
    void onResume(HostType host);
    boolean onOptionsItemSelected(HostType host, MenuItem item);
    void onPause(HostType host);
    void onStop(HostType host);
    void onSaveInstanceState(HostType host, Bundle bundle);
    void onDestroyView(HostType host);
    void onDestroy(HostType host);
    void onDetach(HostType host);
}
