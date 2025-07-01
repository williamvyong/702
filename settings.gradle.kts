pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        // Android Gradle 插件
        id("com.android.application") version "8.1.0" apply false
        id("com.android.library")     version "8.1.0" apply false
        // Kotlin 插件
        id("org.jetbrains.kotlin.android") version "1.9.10" apply false
        // Hilt 插件
        id("com.google.dagger.hilt.android") version "2.47" apply false
        id("com.google.dagger.hilt.android.gradle") version "2.47" apply false
    }
}



dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DebtMake"    // 你项目的根名字
include(":app")
