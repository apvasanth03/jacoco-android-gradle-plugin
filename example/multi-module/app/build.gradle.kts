@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("com.vasanth.jacoco-android")
    id("com.vasanth.jacoco-report-aggregation-android")
}

// region Android
android {
    namespace = "com.vasanth.jacocoandroid.example"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.vasanth.jacocoandroid.example"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
// endregion

// region JACOCO
jacocoReportAggregationAndroid {
    excludes = listOf(
        "**/ui/theme/**",
        "**/*Activity*"
    )
}
// endregion

dependencies {
    // Module
    implementation(project(":library1"))

    // Kotlin
    implementation(libs.kotlin)

    // Android
    implementation(libs.coreKtx)
    implementation(platform(libs.composeBom))
    implementation(libs.bundles.compose)

    // Test
    implementation(libs.junit)
}