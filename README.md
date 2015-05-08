# Light cycle

An Android library to break `BaseActivity` and `BaseFragment` in small testable components. 

## Usage 
```java
public abstract class BaseActivity extends LightCycleActivity {
  @LightCycle ScreenStateProvider screenStateProvider;
  @LightCycle UserAccountController userAccountController;
  [...]
  
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }
}
``` 

```java
class ScreenStateProvider extends DefaultLightCycleActivity<Activity> {
    private boolean isForeground;

    boolean isForeground() {
        return isForeground;
    }

    @Override
    public void onResume(Activity activity) {
        isForeground = true;
    }

    @Override
    public void onPause(Activity activity) {
        isForeground = false;
    }
}
```

Note : The `LightCycle` API is close to the `ActivityLifecycleCallbacks` API available in Andoid 14 [1].

## Principles

### Collaborators over inheritance  

Activities / Fragments are respeonsables for : 
- Inflating layout and views 
- Setting resources 
- Hooking up lightcycles 

Lightcycles are responsible for a specific feature, regarless the Activy or Fragment. 

### Testing the logic 

A typical Light Cycle Activity or Fragment does not contain any logic and may not be tested. 

A Light Cycle should not depend on any resources, meaning it should not be needed to hook up resourouces or mock up views to test it.

```java
public class ScreenStateProviderTest {
    @Mock private Activity activity;
    
    private ScreenStateProvider lightCycle;

    @Before
    public void setUp() throws Exception {
        lightCycle = new ScreenStateProvider();
    }

    @Test
    public void isNotForegroundByDefault() {
        expect(lightCycle.isForeground()).toBeFalse();
    }

    @Test
    public void isForegroundWhenOnResume() {
        lightCycle.onResume(activity);
        expect(lightCycle.isForeground()).toBeTrue();
    }

    @Test
    public void isNotForegroundOnPause() {
        lightCycle.onResume(activity);
        lightCycle.onPause(activity);
        expect(lightCycle.isForeground()).toBeFalse();
    }
[...]
}
```

## Empower 

LightCycles compose well with an injection library. 

Exemple : 
```java
public abstract class BaseActivity extends LightCycleActivity {
  @Inject @Singleton @LightCycle ScreenStateProvider screenStateProvider;
  @Inject @LightCycle UserAccountController userAccountController;
  [...]
  
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }
}
``` 

[1] http://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks.html