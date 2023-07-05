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

dependencies {
    // Kotlin
    implementation(libs.kotlinPlugin)

    // Android
    implementation(libs.androidPlugin)
}