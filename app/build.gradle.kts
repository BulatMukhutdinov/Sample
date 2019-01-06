plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")

    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Config.Android.compileSdk)
    defaultConfig {
        applicationId = Config.Android.applicationId
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName

        minSdkVersion(Config.Android.minSdk)
        targetSdkVersion(Config.Android.targetSdk)

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

    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(Config.Libs.kotlinStdlib)
    implementation(Config.Libs.appcompat)
    implementation(Config.Libs.material)
    implementation(Config.Libs.coreKtx)
    implementation(Config.Libs.constraintLayout)

    // navigation
    implementation(Config.Libs.navigationFragment)
    implementation(Config.Libs.navigationUi)
    implementation(Config.Libs.navigationFragmentKtx)
    implementation(Config.Libs.navigationUiKtx)

    // lifecycle
    implementation(Config.Libs.lifecycleExtensions)
    kapt(Config.Libs.lifecycleCompiler)

    // room
    implementation(Config.Libs.roomRuntime)
    kapt(Config.Libs.roomCompiler)
    implementation(Config.Libs.roomRxjava2)

    // rx
    implementation(Config.Libs.rxandroid)
    implementation(Config.Libs.rxjava)

    // kodein
    implementation(Config.Libs.kodeinDiConfJvm)
    implementation(Config.Libs.kodeinDiFrameworkAndroidX)
    implementation(Config.Libs.kodeinDiGenericJvm)

    // utils
    implementation(Config.Libs.timber)
    implementation(Config.Libs.picasso)
    implementation(Config.Libs.databindingCompiler)
}