package com.vasanth.jacocoandroid.helper.constants

object JacocoAndroidConstants {

    // TASK NAMES
    const val TASK_NAME_UNIT_TEST = "test%sUnitTest"
    const val TASK_NAME_JACOCO_REPORT = "jacoco%sReport"
    const val TASK_NAME_JAVA_COMPILE = "compile%sJavaWithJavac"
    const val TASK_NAME_KOTLIN_COMPILE = "compile%sKotlin"
    const val TASK_NAME_JACOCO_AGGREGATED_CODE_COVERAGE_REPORT =
        "jacoco%sAggregatedCodeCoverageReport"

    // CONFIGURATION NAME
    const val CONFIGURATION_NAME_SOURCE_DIR_ELEMENTS = "%sSourceDirectoriesElements"
    const val CONFIGURATION_NAME_CLASS_DIR_ELEMENTS = "%sClassDirectoriesElements"
    const val CONFIGURATION_NAME_COVERAGE_DATA_ELEMENTS = "%sCoverageDataElements"

    const val CONFIGURATION_NAME_SOURCE_DIR_PATH = "%sSourceDirectoriesPath"
    const val CONFIGURATION_NAME_CLASS_DIR_PATH = "%sClassDirectoriesPath"
    const val CONFIGURATION_NAME_COVERAGE_DATA_PATH = "%sJacocoCoverageDataPath"

    // CONFIGURATION ATTRIBUTES
    const val USAGE_ATTR_JAVA_SOURCE_DIR = "java-source-directories"
    const val USAGE_ATTR_JAVA_CLASS_DIR = "java-class-directories"
    const val USAGE_ATTR_JACOCO_COVERAGE_DATA = "jacoco-coverage-data"

    // OTHERS
    const val EXTENSION_EXECUTION_DATA_FILE = "exec"
}
