package com.vasanth.jacocoandroid.helper.util

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

object JacocoAndroidUtil {

    /**
     * Execute the given action to all the Android Build Variants.
     * If the applied project is
     * - "Android Application" - Get ApplicationVariants
     * - "Android Library" - Get LibraryVariants
     * - Else - Throw error.
     */
    fun executeActionOnAllAndroidBuildVariants(
        project: Project,
        action: (Variant) -> Unit
    ) {
        with(project) {
            val appPlugin = plugins.findPlugin(AppPlugin::class.java)
            val libPlugin = plugins.findPlugin(LibraryPlugin::class.java)

            when {
                (appPlugin != null) -> {
                    val appExtension = extensions.getByType<ApplicationAndroidComponentsExtension>()
                    appExtension.onVariants { applicationVariant ->
                        action(applicationVariant)
                    }
                }

                (libPlugin != null) -> {
                    val libExtension = extensions.getByType<LibraryAndroidComponentsExtension>()
                    libExtension.onVariants { applicationVariant ->
                        action(applicationVariant)
                    }
                }

                else -> {
                    throw GradleException(
                        "Project must apply the Android plugin or the Android library " +
                                "plugin before using the jacoco-android plugin"
                    )
                }
            }
        }
    }
}
