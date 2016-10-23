package com.soundcloud.lightcycle.sample.real_world;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.StringRes;
import android.widget.TextView;

import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.sample.real_world.tracker.Screen;
import com.soundcloud.lightcycle.sample.real_world.tracker.ScreenTracker;

import javax.inject.Inject;

public class HomeActivity extends LightCycle_BaseActivity<HomeActivity> implements Screen {
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
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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
