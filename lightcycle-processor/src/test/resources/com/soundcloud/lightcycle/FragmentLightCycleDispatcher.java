package com.soundcloud.lightcycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


// Because neither the processor or the lib (java libraries) can depend on the api (Android library),
// we have to create a fake `LightCycleActionBarActivity` here for testing purpose.
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentLightCycleDispatcher<T extends Fragment>
        implements LightCycleDispatcher<FragmentLightCycle<T>>, FragmentLightCycle<T> {

    public FragmentLightCycleDispatcher() {
    }

    @Override
    public void bind(FragmentLightCycle<T> lightCycle) { }

    @Override
    public void onAttach(T fragment, Activity activity) { }

    @Override
    public void onAttach(T fragment, Context context) { }

    @Override
    public void onCreate(T fragment, Bundle bundle) { }

    @Override
    public void onViewCreated(T fragment, View view, Bundle savedInstanceState) { }

    @Override
    public void onActivityCreated(T fragment, Bundle bundle) { }

    @Override
    public void onStart(T fragment) { }

    @Override
    public void onResume(T fragment) { }

    @Override
    public boolean onOptionsItemSelected(T fragment, MenuItem item) {
        return false;
    }

    @Override
    public void onPause(T fragment) { }

    @Override
    public void onStop(T fragment) { }

    @Override
    public void onSaveInstanceState(T fragment, Bundle bundle) { }

    @Override
    public void onDestroyView(T fragment) { }

    @Override
    public void onDestroy(T fragment) { }

    @Override
    public void onDetach(T fragment) { }
}
