plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")

    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")

    id("com.google.gms.google-services")
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

    // paging
    implementation(Config.Libs.pagingRuntime)
    implementation(Config.Libs.pagingRxjava2)

    // rx
    implementation(Config.Libs.rxandroid)
    implementation(Config.Libs.rxjava)

    // di
    implementation(Config.Libs.koin)
    implementation(Config.Libs.koinViewModel)
    implementation(Config.Libs.koinScope)

    // firebase
    implementation(Config.Libs.firebaseCore)

    // utils
    implementation(Config.Libs.timber)
    implementation(Config.Libs.jumblr)
    implementation(Config.Libs.picasso)

    implementation(Config.Libs.stetho)
}