package com.soundcloud.android.lightcycle;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

public class LightCycleTestRunner extends RobolectricTestRunner {

    //Maximun SDK Robolectric will compile (issues with SDK > 18)
    private static final int MAX_SDK_SUPPORTED_BY_ROBOLECTRIC = 18;

    private static final String ANDROID_MANIFEST_PATH = "../lightcycle-lib/src/main/AndroidManifest.xml";
    private static final String ANDROID_MANIFEST_RES_PATH = "../lightcycle-lib/src/main/res";

    public LightCycleTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override protected AndroidManifest getAppManifest(Config config) {
        return new AndroidManifest(Fs.fileFromPath(ANDROID_MANIFEST_PATH),
                Fs.fileFromPath(ANDROID_MANIFEST_RES_PATH)) {
            @Override
            public int getTargetSdkVersion() {
                return MAX_SDK_SUPPORTED_BY_ROBOLECTRIC;
            }
        };
    }
}
