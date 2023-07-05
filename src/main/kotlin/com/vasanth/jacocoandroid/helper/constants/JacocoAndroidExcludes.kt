package com.vasanth.jacocoandroid.helper.constants

object JacocoAndroidExcludes {

    // Excludes
    private val EXCLUDES_ANDROID_DATA_BINDING = listOf(
        "android/databinding/**/*.class",
        "**/android/databinding/*Binding.class",
        "**/android/databinding/*",
        "**/androidx/databinding/*",
        "**/BR.*"
    )

    private val EXCLUDES_ANDROID_VIEW_BINDING = listOf(
        "**/databinding/*Binding.class",
        "**/*_ViewBinding*.*",
        "**/*Binding.*"
    )

    private val EXCLUDES_ANDROID = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "androidx/**/*.*"
    )

    private val EXCLUDES_BUTTER_KNIFE = listOf(
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*"
    )

    private val EXCLUDES_DAGGER2 = listOf(
        "**/*Dagger*.*",
        "**/*MembersInjector*.*",
        "**/*_Factory.*",
        "**/*_Provide*Factory*.*",
        "**/*_MembersInjector.class",
        "**/Dagger*Component.class",
        "**/Dagger*Component\$Builder.class",
        "**/*Module_*Factory.class"
    )

    val ANDROID_EXCLUDES = (
            EXCLUDES_ANDROID_DATA_BINDING + EXCLUDES_ANDROID_VIEW_BINDING +
                    EXCLUDES_ANDROID + EXCLUDES_BUTTER_KNIFE +
                    EXCLUDES_DAGGER2
            )
}
