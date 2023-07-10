plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

group = "com.vasanth"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        create("JacocoAndroidPlugin") {
            id = "com.vasanth.jacoco-android"
            implementationClass = "com.vasanth.jacocoandroid.JacocoAndroidPlugin"
        }
        create("JacocoReportAggregationAndroidPlugin") {
            id = "com.vasanth.jacoco-report-aggregation-android"
            implementationClass = "com.vasanth.jacocoandroid.JacocoReportAggregationAndroidPlugin"
        }
    }
}

dependencies {
    // Kotlin
    implementation(libs.kotlinPlugin)

    // Android
    implementation(libs.androidPlugin)
}