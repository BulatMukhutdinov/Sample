object Config {
    object Versions {
        val androidPluginVersion = "3.4.0-alpha10"
        val kotlinVersion = "1.3.11"
        val rxJavaVersion = "2.1.0"
        val lifecycleVersion = "1.1.1"
        val roomVersion = "2.0.0"
        val navigationVersion = "1.0.0-alpha09"
        val kodeinVersion = "5.3.0"
    }

    object BuildPlugins {
        val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidPluginVersion}"
        val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        val navigationSafeArgsGradlePlugin = "android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0-alpha09"
    }

    object Android {
        val applicationId = "ru.bulat.mukhutdinov.mvvm"
        val compileSdk = 28
        val minSdk = 23
        val targetSdk = 28
        val versionCode = 1
        val versionName = "1.0"
    }

    object Libs {
        val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
        val appcompat = "androidx.appcompat:appcompat:1.0.2"
        val material = "com.google.android.material:material:1.0.0"
        val coreKtx = "androidx.core:core-ktx:1.0.1"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"

        val navigationFragment = "android.arch.navigation:navigation-fragment:${Versions.navigationVersion}"
        val navigationUi = "android.arch.navigation:navigation-ui:${Versions.navigationVersion}"
        val navigationFragmentKtx = "android.arch.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
        val navigationUiKtx = "android.arch.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

        val lifecycleExtensions = "android.arch.lifecycle:extensions:${Versions.lifecycleVersion}"
        val lifecycleCompiler = "android.arch.lifecycle:compiler:${Versions.lifecycleVersion}"

        val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
        val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
        val roomRxjava2 = "androidx.room:room-rxjava2:${Versions.roomVersion}"

        //rxJava
        val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxJavaVersion}"
        val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}"

        //kodein
        val kodeinDiGenericJvm = "org.kodein.di:kodein-di-generic-jvm:${Versions.kodeinVersion}"
        val kodeinDiFrameworkAndroidX = "org.kodein.di:kodein-di-framework-android-x:${Versions.kodeinVersion}"
        val kodeinDiConfJvm = "org.kodein.di:kodein-di-conf-jvm:${Versions.kodeinVersion}"

        //utils
        val timber = "com.jakewharton.timber:timber:4.7.1"
        val picasso = "com.squareup.picasso:picasso:2.71828"
        val databindingCompiler = "com.android.databinding:compiler:3.1.4"
    }
}