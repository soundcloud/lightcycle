package com.soundcloud.lightcycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class DefaultActivityLightCycle<T extends Activity> implements ActivityLightCycle<T> {
    @Override
    public void onCreate(T activity, Bundle bundle) { /* no-op */ }

    @Override
    public void onPostCreate(T activity, Bundle bundle) {
        /* no-op */
    }

    @Override
    public void onNewIntent(T activity, Intent intent) { /* no-op */ }

    @Override
    public void onStart(T activity) { /* no-op */ }

    @Override
    public void onResume(T activity) { /* no-op */ }

    @Override
    public boolean onOptionsItemSelected(T activity, MenuItem item) {
        return false;
    }

    @Override
    public void onPause(T activity) { /* no-op */ }

    @Override
    public void onStop(T activity) { /* no-op */ }

    @Override
    public void onSaveInstanceState(T activity, Bundle bundle) { /* no-op */ }

    @Override
    public void onRestoreInstanceState(T activity, Bundle bundle) { /* no-op */ }

    @Override
    public void onDestroy(T activity) { /* no-op */ }
}
