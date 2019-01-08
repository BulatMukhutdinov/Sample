import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.setValue
import org.gradle.kotlin.dsl.repositories

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath (Config.BuildPlugins.androidGradlePlugin)
        classpath (Config.BuildPlugins.kotlinGradlePlugin)
        classpath (Config.BuildPlugins.navigationSafeArgsGradlePlugin)
        classpath (Config.BuildPlugins.googleServices)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}

task("clean") {
    delete(rootProject.buildDir)
}