package com.soundcloud.lightcycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class DefaultSupportFragmentLightCycle<HostType> implements SupportFragmentLightCycle<HostType> {
    @Override
    public void onAttach(HostType host, Activity activity) { /* no-op */ }

    @Override
    public void onCreate(HostType host, Bundle bundle) { /* no-op */ }

    @Override
    public void onViewCreated(HostType host, View view, Bundle savedInstanceState) { /* no-op */ }

    @Override
    public void onActivityCreated(HostType host, Bundle bundle) { /* no-op */ }

    @Override
    public void onStart(HostType host) { /* no-op */ }

    @Override
    public void onResume(HostType host) { /* no-op */ }

    @Override
    public boolean onOptionsItemSelected(HostType host, MenuItem item) {
        return false;
    }

    @Override
    public void onPause(HostType host) { /* no-op */ }

    @Override
    public void onStop(HostType host) { /* no-op */ }

    @Override
    public void onSaveInstanceState(HostType host, Bundle bundle) { /* no-op */ }

    @Override
    public void onDestroyView(HostType host) { /* no-op */ }

    @Override
    public void onDestroy(HostType host) { /* no-op */ }

    @Override
    public void onDetach(HostType host) { /* no-op */ }
}
