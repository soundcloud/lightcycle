package com.soundcloud.lightcycle.sample.real_world.home;

import androidx.annotation.StringRes;

import com.soundcloud.lightcycle.sample.real_world.tracker.Screen;

interface HomeView extends Screen {

    void sayHello(@StringRes int message);

    void showDescription(String title, String message);
}
