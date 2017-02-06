package com.soundcloud.lightcycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class DefaultActivityLightCycle<HostType> implements ActivityLightCycle<HostType> {
    @Override
    public void onCreate(HostType host, Bundle bundle) { /* no-op */ }

    @Override
    public void onNewIntent(HostType host, Intent intent) { /* no-op */ }

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
    public void onRestoreInstanceState(HostType host, Bundle bundle) { /* no-op */ }

    @Override
    public void onDestroy(HostType host) { /* no-op */ }
}
