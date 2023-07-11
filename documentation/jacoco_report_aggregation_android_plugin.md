<!-- TOC -->

* [The JaCoCo Report Aggregation Android Plugin](#the-jacoco-report-aggregation-android-plugin)
    * [Usage](#usage)
    * [Configuring the JaCoCoReportAggregationAndroid Plugin](#configuring-the-jacocoreportaggregationandroid-plugin)
    * [Tasks](#tasks)
    * [Example App](#example-app)

<!-- TOC -->

# The JaCoCo Report Aggregation Android Plugin

The JaCoCo Report Aggregation Android plugin provides the ability to aggregate the results of multiple JaCoCoAndroid
code coverage reports (potentially spanning multiple Gradle projects) into a single HTML report.

## Usage

To use the JaCoCoReportAggregationAndroid plugin, include the following in your build script:

```kotlin
plugins {
    id("com.vasanth.jacoco-report-aggregation-android")
}
```

> **Note :** This plugin takes no action unless JaCoCoAndroid Plugin is applied to All Modules (including App module &
> All library modules)
>
> Refer - [JaCoCo Android Plugin](./jacoco_android_plugin.md)

## Configuring the JaCoCoReportAggregationAndroid Plugin

The JaCoCoReportAggregationAndroid plugin adds a project extension named `jacocoReportAggregationAndroid` of
type `JacocoReportAggregationAndroidPluginExtension`, which allows configuring for JaCoCoReportAggregationAndroid usage
in your build.

```kotlin
jacocoReportAggregationAndroid {
    excludes = listOf(
        "**/ui/theme/**",
        "**/*Activity*"
    )
}
```

Table - JacocoReportAggregationAndroidPluginExtension properties.

| Property | Description                                                                                                                                                                                                                                                                             |
|----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| excludes | List of class names that should be excluded from analysis. Names can use wildcard (* and ?).<br/>Defaults to an empty list.<br/>Exclude - Applies to all projects (including SubProjects).<br/>Uses - ANT style exclude pattern<br/>Refer - https://ant.apache.org/manual/dirtasks.html |

## Tasks

The JaCoCoReportAggregationAndroid plugin automatically adds the following tasks:

- **jacoco[VARIENT_NAME]AggregatedCodeCoverageReport** - Aggregated Code Coverage Report Task to all the build variants.

## Example App

The standard android project with multi module.

Refer - [Example Multi Module](../example/multi-module)