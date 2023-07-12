plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    alias(libs.plugins.gradlePluginPublish)
}

group = "io.github.apvasanth03"
version = "0.1.0-alpha.1"

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

gradlePlugin {
    website.set("https://github.com/apvasanth03/jacoco-android-gradle-plugin")
    vcsUrl.set("https://github.com/apvasanth03/jacoco-android-gradle-plugin")

    plugins {
        create("jacocoAndroidPlugin") {
            id = "io.github.apvasanth03.jacoco-android"
            displayName = "JaCoCo Android Plugin"
            description = "The JaCoCoAndroid plugin provides code coverage metrics for single-module Android project."
            tags.set(listOf("jacoco", "jacocoandroid", "codecoverage", "android"))
            implementationClass = "io.github.apvasanth03.jacocoandroid.JacocoAndroidPlugin"
        }
        create("jacocoReportAggregationAndroidPlugin") {
            id = "io.github.apvasanth03.jacoco-report-aggregation-android"
            displayName = "JaCoCo Report Aggregation Android Plugin"
            description =
                "The JaCoCoReportAggregationAndroidPlugin plugin provides code coverage metrics for multi-module Android project."
            tags.set(listOf("jacoco", "jacocoandroid", "codecoverage", "android"))
            implementationClass = "io.github.apvasanth03.jacocoandroid.JacocoReportAggregationAndroidPlugin"
        }
    }
}

if ((System.getenv("GRADLE_PLUGIN_PUBLISH_KEY") != null) &&
    (System.getenv("GRADLE_PLUGIN_PUBLISH_SECRET") != null)
) {
    project.ext["gradle.publish.key"] = System.getenv("GRADLE_PLUGIN_PUBLISH_KEY")
    project.ext["gradle.publish.secret"] = System.getenv("GRADLE_PLUGIN_PUBLISH_SECRET")
}

dependencies {
    // Kotlin
    implementation(libs.kotlinPlugin)

    // Android
    implementation(libs.androidPlugin)
}