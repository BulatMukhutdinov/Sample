plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("io.fabric")

    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")

    id("com.google.gms.google-services")
}

apply(from = rootProject.file("app/scripts/fabric.gradle"))
apply(from = rootProject.file("app/scripts/signing.gradle"))

android {
    compileSdkVersion(Config.Android.compileSdk)
    defaultConfig {
        applicationId = "${Config.Android.applicationId}.sample"
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName

        minSdkVersion(Config.Android.minSdk)
        targetSdkVersion(Config.Android.targetSdk)

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "ru.bulat.mukhutdinov.sample.SampleAndroidTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dataBinding {
        isEnabled = true
    }

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
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

    // lifecycle
    implementation(Config.Libs.lifecycleExtensions)
    implementation(Config.Libs.lifecycleViewModel)
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
    implementation(Config.Libs.rxbinding)

    // di
    implementation(Config.Libs.koin)
    implementation(Config.Libs.koinViewModel)
    implementation(Config.Libs.koinScope)

    // firebase
    implementation(Config.Libs.firebaseCore)

    // crashlytics
    implementation(Config.Libs.crashlytics) {
        isTransitive = true
    }

    // coroutines
    implementation(Config.Libs.coroutinesCore)
    implementation(Config.Libs.coroutinesAndroid)
    implementation(Config.Libs.coroutinesRx)

    // utils
    implementation(Config.Libs.timber)
    implementation(Config.Libs.jumblr)
    implementation(Config.Libs.picasso)
    implementation(Config.Libs.stetho)

    // unit tests
    testImplementation(Config.Libs.jUnit)
    testImplementation(Config.Libs.mockk)

    // instrumented tests
    androidTestImplementation(Config.Libs.espresso)
    androidTestImplementation(Config.Libs.testRunner)
    androidTestImplementation(Config.Libs.testRules)
    androidTestImplementation(Config.Libs.extJunit)
    androidTestImplementation(Config.Libs.mockkAndroid)
    androidTestImplementation(Config.Libs.jUnit)
    debugImplementation(Config.Libs.fragmentTesting)
}