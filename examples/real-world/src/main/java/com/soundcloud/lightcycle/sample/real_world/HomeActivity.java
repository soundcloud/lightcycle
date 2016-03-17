package com.soundcloud.lightcycle.sample.real_world;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleAppCompatActivity;
import com.soundcloud.lightcycle.sample.real_world.tracker.Screen;
import com.soundcloud.lightcycle.sample.real_world.tracker.ScreenTracker;

import android.support.annotation.StringRes;
import android.widget.TextView;

import javax.inject.Inject;

public class HomeActivity extends LightCycleAppCompatActivity<HomeActivity> implements Screen {
    @Inject @LightCycle ScreenTracker screenTracker;
    @Inject @LightCycle HomePresenter headerPresenter;

    public HomeActivity() {
        SampleApp.getObjectGraph().inject(this);
    }

    @Override
    public String getScreenName() {
        return "HomeActivity";
    }

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_home);
    }

    void sayHello(@StringRes int hello) {
        ((TextView) findViewById(R.id.hello)).setText(hello);
    }

    void showDescription(String title, String description) {
        ((TextView) findViewById(R.id.title)).setText(title);
        ((TextView) findViewById(R.id.description)).setText(description);
    }
}
