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
    id("com.vasanth.jacoco-android")
}
```

```kotlin
// app/build.gradle.kts

plugins {
    id("com.vasanth.jacoco-android")
    id("com.vasanth.jacoco-report-aggregation-android")
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