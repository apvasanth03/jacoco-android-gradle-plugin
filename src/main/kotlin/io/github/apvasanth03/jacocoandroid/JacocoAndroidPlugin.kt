package io.github.apvasanth03.jacocoandroid

import io.github.apvasanth03.jacocoandroid.extension.JacocoAndroidPluginExtension
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.CONFIGURATION_NAME_CLASS_DIR_ELEMENTS
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.CONFIGURATION_NAME_COVERAGE_DATA_ELEMENTS
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.CONFIGURATION_NAME_SOURCE_DIR_ELEMENTS
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.TASK_NAME_JACOCO_REPORT
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
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.register
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * Jacoco Android Plugin
 *
 * The Plugin adds the following Tasks
 * 1. "jacoco[VARIENT_NAME]Report" - "Code Coverage Report" task for all the Build Variants.
 *
 * The Plugin adds the following "Outgoing Variants"
 * 1. "[VARIENT_NAME]SourceDirectoriesElements" - Export "Source Directories" for all Build Variants.
 * 2. "[VARIENT_NAME]ClassDirectoriesElements" - Export "Class Directories" for all Build Variants.
 * 3. "[VARIENT_NAME]CoverageDataElements" - Export "Jacoco Coverage Data" for all Build Variants.
 */
class JacocoAndroidPlugin : Plugin<Project> {

    val PLUGIN_EXTENSION_NAME = "jacocoAndroid"

    override fun apply(project: Project) {
        // Apply "Jacoco Plugin"
        applyJacocoPlugin(project = project)

        // Create "Plugin Extension"
        val pluginExtension = createPluginExtension(project = project)

        // Add "Jacoco Test Coverage Report Task".
        addJacocoTestCoverageReportTaskToAllBuildVariants(
            project = project,
            pluginExtension = pluginExtension
        )

        // Add "Outgoing Variants"
        addOutgoingVariantsForAllBuildVariants(
            project = project
        )
    }

    // region APPLY JACOCO PLUGIN
    private fun applyJacocoPlugin(
        project: Project
    ) {
        with(project) {
            pluginManager.apply(JacocoPlugin::class.java)
        }
    }
    // endregion

    // region CREATE PLUGIN EXTENSION
    private fun createPluginExtension(
        project: Project
    ): JacocoAndroidPluginExtension {
        return with(project) {
            extensions.create(PLUGIN_EXTENSION_NAME, JacocoAndroidPluginExtension::class.java)
        }
    }
    // endregion

    // region ADD TEST COVERAGE REPORT TASK
    /**
     * Add Jacoco Test Coverage Report Task to all Android Build Variants.
     * For each "Build Variant" - Add Task to generate Test Coverage report.
     * Task Name - "jacoco[VARIENT_NAME]Report"
     */
    private fun addJacocoTestCoverageReportTaskToAllBuildVariants(
        project: Project,
        pluginExtension: JacocoAndroidPluginExtension
    ) {
        with(project) {
            executeActionOnAllAndroidBuildVariants(project = project) { variant ->
                val variantNameCapitalize = variant.name.capitalized()
                val unitTestTaskName = TASK_NAME_UNIT_TEST.format(variantNameCapitalize)
                val jacocoReportTaskName = TASK_NAME_JACOCO_REPORT.format(variantNameCapitalize)

                tasks.register(jacocoReportTaskName, JacocoReport::class) {
                    group = LifecycleBasePlugin.VERIFICATION_GROUP
                    description =
                        "Generates code coverage report for the Build Variant $variantNameCapitalize."

                    // Add Dependency on "UnitTest" task.
                    dependsOn(unitTestTaskName)

                    // ExecutionData
                    executionData(tasks.getByName(unitTestTaskName))

                    // Source Directories
                    val sourceDirsProvider = variant.getJavaKotlinSourceDirsProvider()
                    sourceDirsProvider?.let {
                        sourceDirectories.from(sourceDirsProvider)
                    }

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
                }
            }
        }
    }
    // endregion

    // region ADD OUTGOING VARIANTS
    /**
     * Add Outgoing Variants for all Android Build Variants.
     */
    private fun addOutgoingVariantsForAllBuildVariants(
        project: Project
    ) {
        addOutgoingVariantForJavaSourcesDirectories(
            project = project
        )
        addOutgoingVariantForJavaClassDirectories(
            project = project
        )
        addOutgoingVariantForJacocoCoverageData(
            project = project
        )
    }

    /**
     * Add Outgoing Variants for "Source Directories" to all Android Build Variants.
     * Outgoing Configuration - "[VARIENT_NAME]SourceDirectoriesElements"
     */
    private fun addOutgoingVariantForJavaSourcesDirectories(
        project: Project
    ) {
        with(project) {
            executeActionOnAllAndroidBuildVariants(project = project) { variant ->
                val variantName = variant.name
                val sourceDirElementsConfigName =
                    CONFIGURATION_NAME_SOURCE_DIR_ELEMENTS.format(variantName)

                configurations.create(sourceDirElementsConfigName) {
                    isCanBeResolved = false
                    isCanBeConsumed = true

                    // Attributes
                    attributes {
                        configureConfigurationAttributes(
                            project = project,
                            variant = variant,
                            usageAttName = USAGE_ATTR_JAVA_SOURCE_DIR
                        )
                    }

                    // Add SourceDirectories to "OutgoingArtifact"
                    val sourceDirsProvider = variant.getJavaKotlinSourceDirsProvider()
                    sourceDirsProvider?.let {
                        outgoing.artifacts(it)
                    }
                }
            }
        }
    }

    /**
     * Add Outgoing Variants for "Class Directories" to all Android Build Variants.
     * Outgoing Configuration - "[VARIENT_NAME]ClassDirectoriesElements"
     */
    private fun addOutgoingVariantForJavaClassDirectories(
        project: Project
    ) {
        with(project) {
            executeActionOnAllAndroidBuildVariants(project = project) { variant ->
                val variantName = variant.name
                val classDirElementsConfigName =
                    CONFIGURATION_NAME_CLASS_DIR_ELEMENTS.format(variantName)

                configurations.create(classDirElementsConfigName) {
                    isCanBeResolved = false
                    isCanBeConsumed = true

                    // Attributes
                    attributes {
                        configureConfigurationAttributes(
                            project = project,
                            variant = variant,
                            usageAttName = USAGE_ATTR_JAVA_CLASS_DIR
                        )
                    }

                    // Add ClassDirectories to "OutgoingArtifact"
                    val classDirsProvider = variant.getJavaKotlinClassDirsProvider(project)
                    outgoing.artifacts(classDirsProvider)
                }
            }
        }
    }

    /**
     * Add Outgoing Variants for "Jacoco Coverage Data" to all Android Build Variants.
     * Outgoing Configuration - "[VARIENT_NAME]CoverageDataElements"
     */
    private fun addOutgoingVariantForJacocoCoverageData(
        project: Project,
    ) {
        with(project) {
            executeActionOnAllAndroidBuildVariants(project = project) { variant ->
                val variantName = variant.name
                val coverageDataElementsConfigName =
                    CONFIGURATION_NAME_COVERAGE_DATA_ELEMENTS.format(variantName)

                configurations.create(coverageDataElementsConfigName) {
                    isCanBeResolved = false
                    isCanBeConsumed = true

                    // Attributes
                    attributes {
                        configureConfigurationAttributes(
                            project = project,
                            variant = variant,
                            usageAttName = USAGE_ATTR_JACOCO_COVERAGE_DATA
                        )
                    }

                    // Add JacocoCoverageData to "OutgoingArtifact"
                    val variantNameCapitalize = variantName.capitalized()
                    val unitTestTaskName = TASK_NAME_UNIT_TEST.format(variantNameCapitalize)

                    val unitTestTaskProvider = project.provider {
                        tasks.getByName(unitTestTaskName)
                    }
                    val jacocoExecutionDataProvider = unitTestTaskProvider.map {
                        it.extensions.findByType<JacocoTaskExtension>()!!.destinationFile!!
                    }

                    outgoing.artifact(jacocoExecutionDataProvider) {
                        builtBy(unitTestTaskProvider)
                    }
                }
            }
        }
    }
    // endregion
}
