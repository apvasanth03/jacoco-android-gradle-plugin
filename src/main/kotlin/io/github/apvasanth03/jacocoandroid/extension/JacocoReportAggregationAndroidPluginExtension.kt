package io.github.apvasanth03.jacocoandroid.extension

import io.github.apvasanth03.jacocoandroid.JacocoReportAggregationAndroidPlugin

/**
 * Extension for [JacocoReportAggregationAndroidPlugin]
 */
abstract class JacocoReportAggregationAndroidPluginExtension {

    /**
     * List of class names that should be excluded from analysis. Names can use wildcard (* and ?).
     * Defaults to an empty list.
     * Exclude - Applies to all projects (including SubProjects).
     * Uses - ANT style exclude pattern
     * Refer - https://ant.apache.org/manual/dirtasks.html
     */
    var excludes: List<String> = emptyList()

}