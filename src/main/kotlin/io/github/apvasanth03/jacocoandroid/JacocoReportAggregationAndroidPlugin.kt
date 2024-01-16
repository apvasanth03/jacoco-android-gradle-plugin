package io.github.apvasanth03.jacocoandroid

import io.github.apvasanth03.jacocoandroid.extension.JacocoReportAggregationAndroidPluginExtension
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.CONFIGURATION_NAME_CLASS_DIR_PATH
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.CONFIGURATION_NAME_COVERAGE_DATA_PATH
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.CONFIGURATION_NAME_SOURCE_DIR_PATH
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.EXTENSION_EXECUTION_DATA_FILE
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.TASK_NAME_JACOCO_AGGREGATED_CODE_COVERAGE_REPORT
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.TASK_NAME_UNIT_TEST
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.USAGE_ATTR_JACOCO_COVERAGE_DATA
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.USAGE_ATTR_JAVA_CLASS_DIR
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.USAGE_ATTR_JAVA_SOURCE_DIR
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidExcludes.ANDROID_EXCLUDES
import io.github.apvasanth03.jacocoandroid.helper.extension.configureConfigurationAttributes
import io.github.apvasanth03.jacocoandroid.helper.extension.getJavaKotlinClassDirsProvider
import io.github.apvasanth03.jacocoandroid.helper.extension.getJavaKotlinSourceDirsProvider
import io.github.apvasanth03.jacocoandroid.helper.util.JacocoAndroidUtil.executeActionOnAllAndroidBuildVariants
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.register
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * JacocoReportAggregationAndroidPlugin
 *
 * The Plugin provides the ability to aggregate the results of multiple JaCoCo code coverage reports
 * into a single HTML report.
 *
 * The Plugin adds the following Tasks
 * 1. "jacoco[VARIENT_NAME]AggregatedCodeCoverageReport" - "Aggregated Code Coverage Report" Task
 * to all the build variants.
 *
 * This Plugin should be applied to the Application project - Where it collects all Libraries.
 *
 * [JacocoAndroidPlugin] - Should be applied to all Libraries & Application Project.
 */
class JacocoReportAggregationAndroidPlugin : Plugin<Project> {

    val PLUGIN_EXTENSION_NAME = "jacocoReportAggregationAndroid"

    override fun apply(project: Project) {
        // Create "Plugin Extension"
        val pluginExtension = createPluginExtension(project = project)

        // Create "Resolvable Configuration" to Aggregate Artifacts.
        createResolvableConfigurationToAggregateArtifacts(
            project = project
        )

        // Add "Aggregated Code Coverage Report" Task
        addAggregatedCodeCoverageReportTaskToAllBuildVariants(
            project = project,
            pluginExtension = pluginExtension
        )
    }

    // region CREATE PLUGIN EXTENSION
    private fun createPluginExtension(
        project: Project
    ): JacocoReportAggregationAndroidPluginExtension {
        return with(project) {
            extensions.create(
                PLUGIN_EXTENSION_NAME,
                JacocoReportAggregationAndroidPluginExtension::class.java
            )
        }
    }

    // region ADD RESOLVABLE CONFIGURATION TO AGGREGATE ARTIFACTS
    private fun createResolvableConfigurationToAggregateArtifacts(
        project: Project
    ) {
        createConfigurationToAggregateJavaSourcesDirectories(
            project = project
        )
        createConfigurationToAggregateJavaClassDirectories(
            project = project
        )
        createConfigurationToAggregateJacocoCoverageData(
            project = project
        )
    }

    /**
     * Create Resolvable Configuration for "Aggregating All Dependent Java Source Directories"
     * to all Android Build Variants.
     * Configuration - "[VARIENT_NAME]SourceDirectoriesPath"
     */
    private fun createConfigurationToAggregateJavaSourcesDirectories(
        project: Project
    ) {
        with(project) {
            executeActionOnAllAndroidBuildVariants(project = project) { variant ->
                val variantName = variant.name
                val sourceDirPathConfigName = CONFIGURATION_NAME_SOURCE_DIR_PATH.format(variantName)

                configurations.create(sourceDirPathConfigName) {
                    isCanBeResolved = true
                    isCanBeConsumed = false

                    extendsFrom(variant.runtimeConfiguration)

                    attributes {
                        configureConfigurationAttributes(
                            project = project,
                            variant = variant,
                            usageAttName = USAGE_ATTR_JAVA_SOURCE_DIR
                        )
                    }
                }
            }
        }
    }

    /**
     * Create Resolvable Configuration for "Aggregating All Dependent Java Class Directories"
     * to all Android Build Variants.
     * Configuration - "[VARIENT_NAME]ClassDirectoriesPath"
     */
    private fun createConfigurationToAggregateJavaClassDirectories(
        project: Project
    ) {
        with(project) {
            executeActionOnAllAndroidBuildVariants(project = project) { variant ->
                val variantName = variant.name
                val classDirPathConfigName = CONFIGURATION_NAME_CLASS_DIR_PATH.format(variantName)

                configurations.create(classDirPathConfigName) {
                    isCanBeResolved = true
                    isCanBeConsumed = false

                    extendsFrom(variant.runtimeConfiguration)

                    attributes {
                        configureConfigurationAttributes(
                            project = project,
                            variant = variant,
                            usageAttName = USAGE_ATTR_JAVA_CLASS_DIR
                        )
                    }
                }
            }
        }
    }

    /**
     * Create Resolvable Configuration for "Aggregating All Dependent Jacoco Coverage Data"
     * to all Android Build Variants.
     * Configuration - "[VARIENT_NAME]JacocoCoverageDataPath"
     */
    private fun createConfigurationToAggregateJacocoCoverageData(
        project: Project
    ) {
        with(project) {
            executeActionOnAllAndroidBuildVariants(project = project) { variant ->
                val variantName = variant.name
                val jacocoCoverageDataPathConfigName =
                    CONFIGURATION_NAME_COVERAGE_DATA_PATH.format(variantName)

                configurations.create(jacocoCoverageDataPathConfigName) {
                    isCanBeResolved = true
                    isCanBeConsumed = false

                    extendsFrom(variant.runtimeConfiguration)

                    attributes {
                        configureConfigurationAttributes(
                            project = project,
                            variant = variant,
                            usageAttName = USAGE_ATTR_JACOCO_COVERAGE_DATA
                        )
                    }
                }
            }
        }
    }
    // endregion

    // region ADD AGGREGATE CODE COVERAGE REPORT TASK
    /**
     * Add "Aggregated Code Coverage Report" Task to all Android Build Variants.
     * For each "Build Variant" - Add Task to generate Aggregated Code Coverage Report.
     * Task Name - "jacoco[VARIENT_NAME]AggregatedCodeCoverageReport"
     */
    private fun addAggregatedCodeCoverageReportTaskToAllBuildVariants(
        project: Project,
        pluginExtension: JacocoReportAggregationAndroidPluginExtension
    ) {
        with(project) {
            executeActionOnAllAndroidBuildVariants(project = project) { variant ->
                val variantName = variant.name
                val variantNameCapitalize = variantName.capitalized()
                val jacocoAggregatedReportTaskName =
                    TASK_NAME_JACOCO_AGGREGATED_CODE_COVERAGE_REPORT.format(variantNameCapitalize)

                project.tasks.register(jacocoAggregatedReportTaskName, JacocoReport::class) {
                    group = VERIFICATION_GROUP
                    description =
                        "Generates Aggregated code coverage report for the Build Variant $variantNameCapitalize."

                    // Add Dependency on "UnitTest" task.
                    val unitTestTaskName = TASK_NAME_UNIT_TEST.format(variantNameCapitalize)
                    dependsOn(unitTestTaskName)

                    // ExecutionData
                    executionData(project.tasks.getByName(unitTestTaskName))

                    val jacocoCoverageDataPathConfiguration = project.configurations.getByName(
                        CONFIGURATION_NAME_COVERAGE_DATA_PATH.format(variantName)
                    )
                    val dependentExecutionDatas =
                        jacocoCoverageDataPathConfiguration.incoming.artifactView {
                            lenient(true)
                        }.files.filter {
                            (it.extension == EXTENSION_EXECUTION_DATA_FILE)
                        }
                    executionData(dependentExecutionDatas)

                    // Source Directories
                    val sourceDirsProvider = variant.getJavaKotlinSourceDirsProvider()
                    sourceDirsProvider?.let {
                        sourceDirectories.from(sourceDirsProvider)
                    }

                    val sourceDirPathConfiguration = project.configurations.getByName(
                        CONFIGURATION_NAME_SOURCE_DIR_PATH.format(variantName)
                    )
                    val dependentSourceDirs =
                        sourceDirPathConfiguration.incoming.artifactView {
                            lenient(true)
                        }.files.filter {
                            it.isDirectory
                        }
                    sourceDirectories.from(dependentSourceDirs)

                    // Class Directories
                    val classDirsProvider = variant.getJavaKotlinClassDirsProvider(project)
                        .map { directories ->
                            directories.map {
                                fileTree(it) {
                                    exclude(ANDROID_EXCLUDES)
                                    exclude(pluginExtension.excludes)
                                }
                            }
                        }
                    classDirectories.from(classDirsProvider)

                    val classDirPathConfiguration = project.configurations.getByName(
                        CONFIGURATION_NAME_CLASS_DIR_PATH.format(variantName)
                    )
                    val dependentClassDirs = project.provider {
                        classDirPathConfiguration.incoming.artifactView {
                            lenient(true)
                        }.files.filter {
                            it.isDirectory
                        }.map {
                            project.fileTree(it) {
                                exclude(ANDROID_EXCLUDES)
                                exclude(pluginExtension.excludes)
                            }
                        }
                    }
                    classDirectories.from(dependentClassDirs)
                }
            }
        }
    }
    // endregion
}
