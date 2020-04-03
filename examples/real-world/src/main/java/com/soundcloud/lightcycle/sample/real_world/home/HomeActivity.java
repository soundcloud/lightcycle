package com.soundcloud.lightcycle.sample.real_world.home;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleAppCompatActivity;
import com.soundcloud.lightcycle.sample.real_world.R;
import com.soundcloud.lightcycle.sample.real_world.SampleApp;
import com.soundcloud.lightcycle.sample.real_world.tracker.ScreenTracker;

import android.widget.TextView;

import androidx.annotation.StringRes;

import javax.inject.Inject;

public class HomeActivity extends LightCycleAppCompatActivity<HomeView> implements HomeView {
    @Inject @LightCycle ScreenTracker screenTracker;
    @Inject @LightCycle HomePresenter headerPresenter;

    public HomeActivity() {
        SampleApp.getObjectGraph().inject(this);
    }

    @Override
    public String getScreenName() {
        return "HomeScreen";
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
