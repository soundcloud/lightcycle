package com.soundcloud.lightcycle.sample.real_world.home;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.sample.real_world.MyLightCycleAppCompatActivity;
import com.soundcloud.lightcycle.sample.real_world.R;
import com.soundcloud.lightcycle.sample.real_world.SampleApp;
import com.soundcloud.lightcycle.sample.real_world.tracker.ScreenTracker;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.StringRes;
import android.widget.TextView;

import javax.inject.Inject;

public class HomeActivity
        extends MyLightCycleAppCompatActivity
        implements HomeView {

    @Inject @LightCycle public ScreenTracker screenTracker;
    @Inject @LightCycle public HomePresenter headerPresenter;

    public HomeActivity() {
        SampleApp.getObjectGraph().inject(this);
    }

    @Override
    public String getScreenName() {
        return "HomeScreen";
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    public void sayHello(@StringRes int message) {
        ((TextView) findViewById(R.id.hello)).setText(message);
    }

    @Override
    public void showDescription(String title, String message) {
        ((TextView) findViewById(R.id.title)).setText(title);
        ((TextView) findViewById(R.id.description)).setText(message);
    }

}
