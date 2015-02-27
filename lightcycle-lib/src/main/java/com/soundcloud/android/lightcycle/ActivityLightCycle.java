package com.soundcloud.android.lightcycle;

import org.jetbrains.annotations.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public interface ActivityLightCycle<T extends Activity> {
    void onCreate(T activity, @Nullable Bundle bundle);
    void onNewIntent(T activity, Intent intent);
    void onStart(T activity);
    void onResume(T activity);
    boolean onOptionsItemSelected(T activity, MenuItem item);
    void onPause(T activity);
    void onStop(T activity);
    void onSaveInstanceState(T activity, Bundle bundle);
    void onRestoreInstanceState(T activity, Bundle bundle);
    void onDestroy(T activity);
}
