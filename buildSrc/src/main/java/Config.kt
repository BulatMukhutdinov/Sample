object Config {
    object Versions {
        val androidPlugin = "3.5.0-alpha05"
        val kotlin = "1.3.20"
        val rxJava = "2.1.0"
        val lifecycle = "2.0.0."
        val room = "2.1.0-alpha03"
        val navigatio = "1.0.0-alpha09"
        val koin = "2.0.0-beta-1"
        val paging = "2.1.0-rc01"
        val retrofit = "2.5.0"
        val gms = "15.0.1"
    }

    object BuildPlugins {
        val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidPlugin}"
        val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        val navigationSafeArgsGradlePlugin = "android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0-alpha09"
        val googleServices = "com.google.gms:google-services:4.0.1"
        val fabricGradlePlugin = "io.fabric.tools:gradle:1.27.1"
    }

    object Android {
        val applicationId = "ru.bulat.mukhutdinov"
        val compileSdk = 28
        val minSdk = 24
        val targetSdk = 28
        val versionCode = 1
        val versionName = "1.0"
    }

    object Libs {
        val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        val appcompat = "androidx.appcompat:appcompat:1.1.0-alpha1"
        val material = "com.google.android.material:material:1.1.0-alpha03"
        val coreKtx = "androidx.core:core-ktx:1.0.1"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"

        // navigation
        val navigationFragment = "android.arch.navigation:navigation-fragment:${Versions.navigatio}"
        val navigationUi = "android.arch.navigation:navigation-ui:${Versions.navigatio}"
        val navigationFragmentKtx = "android.arch.navigation:navigation-fragment-ktx:${Versions.navigatio}"
        val navigationUiKtx = "android.arch.navigation:navigation-ui-ktx:${Versions.navigatio}"

        // lifecycle
        val lifecycleExtensions = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
        val lifecycleCompiler = "android.arch.lifecycle:compiler:${Versions.lifecycle}"

        // room
        val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        val roomRxjava2 = "androidx.room:room-rxjava2:${Versions.room}"

        // paging
        val pagingRuntime = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
        val pagingRxjava2 = "androidx.paging:paging-rxjava2-ktx:${Versions.paging}"

        // rxJava
        val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxJava}"
        val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
        val rxbinding = "com.jakewharton.rxbinding3:rxbinding:3.0.0-alpha2"

        // koin
        val koin = "org.koin:koin-android:${Versions.koin}"
        val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}"

        // firebase
        val firebaseCore = "com.google.firebase:firebase-core:16.0.1"
        val firebaseDynamicLinks = "com.google.firebase:firebase-dynamic-links:16.0.1"

        // network
        val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.9.0"

        // google services
        val playServicesAuth = "com.google.android.gms:play-services-auth_activity:${Versions.gms}"

        // fabric
        val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.9.9@aar"

        // utils
        val timber = "com.jakewharton.timber:timber:4.7.1"
        val jumblr = "com.tumblr:jumblr:0.0.13"
        val picasso = "com.squareup.picasso:picasso:2.71828"
        val stetho = "com.facebook.stetho:stetho:1.5.0"
    }
}