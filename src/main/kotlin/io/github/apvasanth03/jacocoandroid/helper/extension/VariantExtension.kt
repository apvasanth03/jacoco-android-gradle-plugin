package io.github.apvasanth03.jacocoandroid.helper.extension

import com.android.build.api.variant.Variant
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.TASK_NAME_JAVA_COMPILE
import io.github.apvasanth03.jacocoandroid.helper.constants.JacocoAndroidConstants.TASK_NAME_KOTLIN_COMPILE
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * [Variant] Extension - to Get Provider for Java & Kotlin source directories.
 */
fun Variant.getJavaKotlinSourceDirsProvider(): Provider<out Collection<Directory>>? {
    val kotlinDirsProvider = sources.kotlin?.all
    val javaDirsProvider = sources.java?.all
    val sourceDirsProvider = (kotlinDirsProvider ?: javaDirsProvider)
    return sourceDirsProvider
}

/**
 * [Variant] Extension - to Get Provider for Java & Kotlin class directories.
 */
fun Variant.getJavaKotlinClassDirsProvider(
    project: Project
): Provider<out Collection<Directory>> {
    val classDirsProvider = project.provider {
        val variantNameCapitalize = name.capitalized()
        val javaCompileTaskName = TASK_NAME_JAVA_COMPILE.format(variantNameCapitalize)
        val kotlinCompileTaskName = TASK_NAME_KOTLIN_COMPILE.format(variantNameCapitalize)

        val javaClassDirs = project.tasks.getByName(
            javaCompileTaskName,
            JavaCompile::class
        ).destinationDirectory.get()
        val kotlinClassDirs = project.tasks.getByName(
            kotlinCompileTaskName,
            KotlinCompile::class
        ).destinationDirectory.get()

        val classDirs = listOf(javaClassDirs, kotlinClassDirs)

        classDirs
    }

    return classDirsProvider
}