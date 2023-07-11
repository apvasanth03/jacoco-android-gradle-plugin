<!-- TOC -->

* [The JacocoAndroid Plugin](#the-jacocoandroid-plugin)
    * [Configuring the JaCoCoAndroid Plugin](#configuring-the-jacocoandroid-plugin)
    * [Configuring the JaCoCo Plugin](#configuring-the-jacoco-plugin)
    * [JaCoCo Report configuration](#jacoco-report-configuration)
    * [Tasks](#tasks)
    * [Outgoing Variants](#outgoing-variants)
    * [Example App](#example-app)

<!-- TOC -->

# The JaCoCo Android Plugin

The JaCoCo Android plugin provides code coverage metrics for Android code

## Configuring the JaCoCoAndroid Plugin

The JaCoCoAndroid plugin adds a project extension named `jacocoAndroid` of type `JacocoAndroidPluginExtension`, which
allows configuring for JaCoCoAndroid usage in your build.

```kotlin
jacocoAndroid {
    excludes = listOf(
        "**/ui/theme/**",
        "**/*Activity*"
    )
}
```

Table - JacocoAndroidPluginExtension properties.

| Property | Description                                                                                                                                                                                                              |
|----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| excludes | List of class names that should be excluded from analysis. Names can use wildcard (* and ?).<br/>Defaults to an empty list.<br/>Uses - ANT style exclude pattern<br/>Refer - https://ant.apache.org/manual/dirtasks.html |

## Configuring the JaCoCo Plugin

The JaCoCoAndroid plugin is built on top of Jacoco Plugin, hence we can use Jacoco Plugin to configuring defaults for
JaCoCo usage in your build.

Refer - https://docs.gradle.org/current/userguide/jacoco_plugin.html

```kotlin
jacoco {
    toolVersion = "0.8.9"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}
```

Table - Gradle defaults for JaCoCo properties.

| Property         | Gradle default           |
|------------------|--------------------------|
| reportsDirectory | $buildDir/reports/jacoco |

## JaCoCo Report configuration

The [JacocoReport](https://docs.gradle.org/current/dsl/org.gradle.testing.jacoco.tasks.JacocoReport.html) task is
used to generate code coverage reports in different formats. It implements the standard Gradle
type [Reporting](https://docs.gradle.org/current/dsl/org.gradle.api.reporting.Reporting.html) and exposes a report
container of
type [JacocoReportsContainer](https://docs.gradle.org/current/javadoc/org/gradle/testing/jacoco/tasks/JacocoReportsContainer.html)
.

```kotlin
tasks.withType(JacocoReport::class.java).configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}
```

## Tasks

The JaCoCoAndroid plugin automatically adds the following tasks:

- **jacoco[VARIENT_NAME]Report** - Code Coverage Report task for all the Build Variants.

## Outgoing Variants

The JaCoCoAndroid Plugin adds the following Outgoing Variants

- **[VARIENT_NAME]SourceDirectoriesElements** - Export "Source Directories" for all Build Variants.
  <br/><br/>
- **[VARIENT_NAME]ClassDirectoriesElements** - Export "Class Directories" for all Build Variants.
  <br/><br/>
- **[VARIENT_NAME]CoverageDataElements** - Export "Jacoco Coverage Data" for all Build Variants.

## Example App

The standard android project with single module.

Refer - [Example Single Module](../example/single-module/)




