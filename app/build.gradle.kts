plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "yalexaner.messages"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        useIR = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

dependencies {
    implementation(Libraries.External.permissions)

    implementation(Libraries.Android.core)
    implementation(Libraries.Android.appcompat)
    implementation(Libraries.Android.material)

    implementation(Libraries.Compose.ui)
    implementation(Libraries.Compose.uiTooling)
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Compose.livedata)
    implementation(Libraries.Compose.navigation)

    implementation(Libraries.Hilt.core)
    kapt(Libraries.Hilt.compiler)
    implementation(Libraries.Hilt.viewmodel)
    implementation(Libraries.Hilt.navigation)
    kapt(Libraries.Hilt.androidx)

    implementation(Libraries.Lifecycle.viewmodel)
    implementation(Libraries.Lifecycle.livedata)
    implementation(Libraries.Lifecycle.runtime)

    testImplementation(Libraries.Test.core)
    androidTestImplementation(Libraries.Test.junit)
    androidTestImplementation(Libraries.Test.espresso)
}