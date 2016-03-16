package com.soundcloud.lightcycle.sample.real_world;

import static org.mockito.Mockito.verify;

import com.soundcloud.lightcycle.sample.real_world.utils.DateProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.os.Bundle;

@RunWith(MockitoJUnitRunner.class)
public class HeaderPresenterTest {
    @Mock private HomeActivity activity;

    @Test
    public void sayHelloWhenNotMorning() {
        HeaderPresenter presenter = new HeaderPresenter(new DateProvider() {
            @Override
            public boolean isMorning() {
                return false;
            }
        });

        presenter.onCreate(activity, Bundle.EMPTY);

        verify(activity).sayHello(R.string.hello);
    }

    @Test
    public void sayGoodMorning() {
        HeaderPresenter presenter = new HeaderPresenter(new DateProvider() {
            @Override
            public boolean isMorning() {
                return true;
            }
        });

        presenter.onCreate(activity, Bundle.EMPTY);

        verify(activity).sayHello(R.string.good_morning);
    }
}
