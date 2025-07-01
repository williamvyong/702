pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        // Android + Kotlin 插件
        id("com.android.application") version "8.1.1" apply false
        id("com.android.library")     version "8.1.1" apply false
        kotlin("android")             version "1.9.20" apply false
        kotlin("kapt")                version "1.9.20" apply false

        // Hilt Gradle 插件
        id("com.google.dagger.hilt.android") version "2.46.1" apply false
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DebtMake"
include(":app")
