# LightCycle

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

LightCycle is a library that facilitates breaking out concerns of your Activities and Fragments into small, self-contained components called LightCycles.

Fields implementing the LightCycle's API and annotated with `@LightCycle` within an `LightCycleActivity` or `LightCycleFragment` will be bound to the given Activity or Fragment lifecycle. 

## Usage 

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
private class MyController extends DefaultActivityLightCycle<MyActivity> {

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

LightCycle encourages composition over inheritance. We believe it helps to write more readable, maintainable and testable code. It works particularly well when combined with dependency injection.

The responsibilities are defined as follow:
- Activities & fragments are responsible for:
 - Inflating layout - and more generally loading android resources.
 - Declaring LightCycles
- LightCycles are responsible for a specific domain logic. They are agnostic to each other. *For example: Presenters, such as PlaylistPresenter, controllers, such as ChromeCastConnectionController*.

It is important to note that there is no guarantee of order when notifying LightCycles. 

## Examples

You can find some examples here:
- [basic example.](examples/basic)
- ["real-world" example.](examples/real-world)

## Documentation 

### LightCycle

There are 3 types of LightCycles - the API is comparable to the  [`ActivityLifecycleCallbacks`](http://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks.html) from the Android SDK:
- `ActivityLightCycle`
- `FragmentLightCycle`
- `SupportFragmentLightCycle`

For convenience default implementations are provided:
- `DefaultActivityLightCycle`
- `DefaultFragmentLightCycle`
- `DefaultSupportFragmentLightCycle`

### Dispatcher

Like its name indicates, it dispatches an Activity or Fragment's lifecycle to any LightCycles. The API defines a single method: `bind`.

The interface:
- `LightCycleDispatcher`

### Built-in dispatchers. 

Three types of dispatchers are provided:
- `ActivityLightCycleDispatcher`
- `FragmentLightCycleDispatcher`
- `SupportFragmentLightCycleDispatcher`

Notably, these built-in classes are both dispatchers and LightCycles. This means you can nest `LightCycle`. 

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
private class MyController extends ActivityLightCycleDispatcher<MyActivity> {
    @LightCycle MySubController1 controller = new MyController1();
    @LightCycle MySubController2 controller = new MyController2();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        [...] // <- specific init 
        super.onCreate(savedInstanceState) // <- call super to dispatch.
    }
    
}
```

### Provided base activities

Base activities/fragments are also dispatchers. 

The following base activities are provided so far:
- `LightCycleActionBarActivity`
- `LightCycleAppCompatActivity`
- `LightCycleFragment`
- `LightCycleSupportFragment`

## Integration 

Not yet available on maven central.
