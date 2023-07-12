# Example App - For JaCoCoReportAggregationAndroid Plugin

## JaCoCoReportAggregationAndroid Plugin

The JaCoCoReportAggregationAndroid plugin provides code coverage metrics for multi-module Android project.

Refer - [The JaCoCoReportAggregationAndroid Plugin](../../documentation/jacoco_report_aggregation_android_plugin.md)

## Example App

The standard android project with multi module.

### Apply & Configure JaCoCoAndroid Plugin

```kotlin
// library1/build.gradle.kts

plugins {
    id("io.github.apvasanth03.jacoco-android") version "0.1.0-alpha.1"
}
```

```kotlin
// app/build.gradle.kts

plugins {
    id("io.github.apvasanth03.jacoco-android") version "0.1.0-alpha.1"
    id("io.github.apvasanth03.jacoco-report-aggregation-android") version "0.1.0-alpha.1"
}

jacocoAndroid {
    excludes = listOf(
        "**/ui/theme/**",
        "**/*Activity*"
    )
}
```

### Tasks

The JaCoCoReportAggregationAndroid plugin adds the following tasks,

- jacocoDebugAggregatedCodeCoverageReport
- jacocoReleaseAggregatedCodeCoverageReport

When we execute the task, the corresponding `UnitTest` task in all modules will be executed and aggregated code coverage
report will be generated at `app/build/reports/jacoco/` 