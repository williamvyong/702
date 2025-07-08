buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Android Gradle Plugin
        classpath("com.android.tools.build:gradle:8.6.0")
        // Kotlin Gradle Plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.0")
        // Hilt Gradle Plugin
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
        // 其他需要的 classpath 都可以加
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}