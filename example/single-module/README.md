# Example App - For JacocoAndroid Plugin

## Jacoco Android Plugin

The JaCoCoAndroid plugin provides code coverage metrics for single-module Android project.

Refer - [The JacocoAndroid Plugin](../../documentation/jacoco_android_plugin.md)

## Example App

The standard android project with single module.

### Apply & Configure JaCoCoAndroid Plugin

```kotlin
// app/build.gradle.kts

plugins {
    id("io.github.apvasanth03.jacoco-android") version "0.1.0-alpha.1"
}

jacocoAndroid {
    excludes = listOf(
        "**/ui/theme/**",
        "**/*Activity*"
    )
}
```

### Tasks

The JacocoAndroid plugin adds the following tasks,

- jacocoDebugReport
- jacocoReleaseReport

When we execute the task, the corresponding `UnitTest` task will be executed and code coverage report will be generated
at `app/build/reports/jacoco/` 