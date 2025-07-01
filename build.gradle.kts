buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}

plugins {
    // 只给 Android/Kotlin 用，Hilt 插件放到 buildscript 里
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}