plugins {
    id("com.android.application")

    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Config.Android.compileSdk)
    defaultConfig {
        applicationId = "${Config.Android.applicationId}.customsharedaccount"
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName

        minSdkVersion(Config.Android.minSdk)
        targetSdkVersion(Config.Android.targetSdk)

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(Config.Libs.kotlinStdlib)
    implementation(Config.Libs.appcompat)
    implementation(Config.Libs.material)
    implementation(Config.Libs.coreKtx)
    implementation(Config.Libs.constraintLayout)
}