# LightCycle

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

LightCycle is an Android library that helps break logic out of `Activity` and `Fragment` classes into small, self-contained components called LightCycles.

Fields that are annotated `@LightCycle` and implement the LightCycle API within a `LightCycleActivity` or `LightCycleFragment` will be bound to that `Activity` or `Fragment` lifecycle. 

For more information please see the [website](http://soundcloud.github.io/lightcycle/).

## Examples

- [basic](https://github.com/soundcloud/lightcycle/tree/master/examples/basic)
- ["real world"](https://github.com/soundcloud/lightcycle/tree/master/examples/real-world)

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
