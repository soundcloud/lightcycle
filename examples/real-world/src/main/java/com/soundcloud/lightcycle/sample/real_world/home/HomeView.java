package com.soundcloud.lightcycle.sample.real_world.home;

import com.soundcloud.lightcycle.sample.real_world.tracker.Screen;

import android.support.annotation.StringRes;

public interface HomeView extends Screen {

    void sayHello(@StringRes int message);

    void showDescription(String title, String message);
}
