package com.soundcloud.lightcycle.sample.real_world;

import dagger.ObjectGraph;

import android.app.Application;

public class SampleApp extends Application {

    private static SampleApp instance;

    protected ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new ApplicationModule());
        instance = this;
    }

    public static ObjectGraph getObjectGraph() {
        if (instance == null || instance.objectGraph == null) {
            throw new IllegalStateException("Cannot access the app graph before the application has been created");
        }
        return instance.objectGraph;
    }
}
