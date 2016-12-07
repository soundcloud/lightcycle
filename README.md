# LightCycle

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

LightCycle is an Android library that helps break logic out of `Activity` and `Fragment` classes into small, self-contained components called LightCycles.

Fields that are annotated `@LightCycle` and implement the LightCycle API within a `LightCycleActivity` or `LightCycleFragment` will be bound to that `Activity` or `Fragment` lifecycle. 

## Usage 

```java
public class MyActivity extends LightCycleAppCompatActivity<MyActivity> {
    @LightCycle MyController controller = new MyController();

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.main);
    }
}
```

```java
public class MyController extends DefaultActivityLightCycle<MyActivity> {

    @Override
    public void onPause(MyActivity activity) {
        // MyActivity was paused
    }
    
    [...]

    @Override
    public void onResume(MyActivity activity) {
        // MyActivity was resumed
    }
}
```

## Philosophy

LightCycle lets self-contained classes respond to Android’s lifecycle events. This supports composition over inheritance and promotes components that follow the single responsibility principle. We believe it helps us write more readable, maintainable and testable code. It works particularly well alongside dependency injection.

- `Activity` & `Fragment` classes:
 - Inflate layouts and configure Android specifics
 - Declare LightCycles
- A LightCycle component is responsible for an isolated chunk of logic (such as presentation, tracking etc.)

A LightCycle doesn't know about other LightCycles. There is no guarantee for ordering when multiple LightCycles receive the same lifecycle callback. 

## Examples

- [basic](https://github.com/soundcloud/lightcycle/tree/master/examples/basic)
- ["real world"](https://github.com/soundcloud/lightcycle/tree/master/examples/real-world)

## Documentation 

### LightCycle

There are 3 types of LightCycles - the API is comparable to the  [`ActivityLifecycleCallbacks`](http://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks.html) from the Android SDK:
- `ActivityLightCycle`
- `FragmentLightCycle`
- `SupportFragmentLightCycle`

For convenience, default implementations are provided:
- `DefaultActivityLightCycle`
- `DefaultFragmentLightCycle`
- `DefaultSupportFragmentLightCycle`

### Dispatcher

This dispatches an `Activity` or `Fragment` lifecycle callback to attached LightCycles. The API defines a single  `bind` method. See the `LightCycleDispatcher` interface.

#### Built-in dispatchers

Three types of dispatchers are provided:
- `ActivityLightCycleDispatcher`
- `FragmentLightCycleDispatcher`
- `SupportFragmentLightCycleDispatcher`

Note: these built-in classes are both dispatchers and LightCycles, meaning that you can nest LightCycles. 

```java
public class MyActivity extends LightCycleAppCompatActivity<MyBaseActivity> {
    @LightCycle MyController controller = new MyController();

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.main);
    }
}
```

```java
public class MyController extends ActivityLightCycleDispatcher<MyActivity> {
    @LightCycle MySubController1 controller = new MyController1();
    @LightCycle MySubController2 controller = new MyController2();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LightCycles.bind(this) // <- bind the lightcycles
        [...] // <- specific init 
        super.onCreate(savedInstanceState) // <- call super to dispatch.
    }
}
```

#### Provided base class dispatchers

The following base activities are provided so far:
- `LightCycleActionBarActivity`
- `LightCycleAppCompatActivity`
- `LightCycleFragment`
- `LightCycleSupportFragment`

#### Adding LightCycle to your own base `Activity` or `Fragment`

/!\ Please, read carefully the following steps (item 2 in particular).

To add LightCycles to your `MyBaseActivity`, your `Activity` must: 
- Implement the `LightCycleDispatcher` interface. _Note: The processor needs to know the exact type being dispatched, so if your base activity is templated then the activities inheriting from it must explicitly `implements LightCycleDispatcher<ActivityLightCycle<YourActivity>>`_ (for more details, refer to https://github.com/soundcloud/lightcycle/issues/49)
- Dispatch all the lifecycle methods
- Bind fields annotated `@LightCycle` with `LightCycles.bind(this)`

The same technique applies for `Fragment`. 

```java
public class MyBaseActivity extends Activity implements LightCycleDispatcher<ActivityLightCycle<MyBaseActivity>> {

    private final ActivityLightCycleDispatcher<MyBaseActivity> lightCycleDispatcher;

    @Override
    public void bind(ActivityLightCycle<MyBaseActivity> lightCycle) {
        lightCycleDispatcher.bind(lightCycle);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LightCycles.bind(this);
        lightCycleDispatcher.onCreate((MyBaseActivity) this, savedInstanceState);
    }
    
    [...]
    
    @Override
    protected void onDestroy() {
        lightCycleDispatcher.onDestroy((MyBaseActivity) this);
        super.onDestroy();
    }
}
```

See for example [LightCycleActionBarActivity](lightcycle-lib/src/main/java/com/soundcloud/lightcycle/LightCycleActionBarActivity.java) or [LightCycleSupportFragment](lightcycle-lib/src/main/java/com/soundcloud/lightcycle/LightCycleSupportFragment.java). 

## Build integration 

Gradle:

```gradle
ext.lightCycleVersion=<LATEST_VERSION>

dependencies {
  compile "com.soundcloud.lightcycle:lightcycle-lib:$lightCycleVersion"
  annotationProcessor "com.soundcloud.lightcycle:lightcycle-processor:$lightCycleVersion"
}
```

Or if you're using a version of the Android gradle plugin below `2.2.0`

```gradle
buildscript {
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}

apply plugin: 'com.neenbedankt.android-apt'

ext.lightCycleVersion=<LATEST_VERSION>

dependencies {
  compile "com.soundcloud.lightcycle:lightcycle-lib:$lightCycleVersion"
  apt "com.soundcloud.lightcycle:lightcycle-processor:$lightCycleVersion"
}
```
