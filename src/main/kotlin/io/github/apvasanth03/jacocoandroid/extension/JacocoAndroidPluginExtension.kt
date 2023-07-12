package io.github.apvasanth03.jacocoandroid.extension

import io.github.apvasanth03.jacocoandroid.JacocoAndroidPlugin

/**
 * Extension for [JacocoAndroidPlugin]
 */
abstract class JacocoAndroidPluginExtension {

    /**
     * List of class names that should be excluded from analysis. Names can use wildcard (* and ?).
     * Defaults to an empty list.
     * Uses - ANT style exclude pattern
     * Refer - https://ant.apache.org/manual/dirtasks.html
     */
    var excludes: List<String> = emptyList()

}