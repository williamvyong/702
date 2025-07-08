plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.0"
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.2.0"
}

android {
    namespace = "com.williamv.debtmake"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.williamv.debtmake"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }
}

dependencies {
    // ------------------ Jetpack Compose BOM（统一Compose依赖版本）------------------
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    // Kotlin stblib
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.0")
    // ------------------ Kotlin 协程（异步编程，Android必备）------------------
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    // AndroidX
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")

    // ------------------ Jetpack Compose UI 基础库 ------------------
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.activity:activity-compose:1.9.0")
    // ...（你原有的 compose 相关其它依赖，也可以保留）

    // ------------------ Lifecycle 相关，2.8.1 是适配 AGP 8.3.0 的最高版 ------------------
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.1")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Room
    implementation("androidx.room:room-runtime:2.7.2")
    implementation("androidx.room:room-ktx:2.7.2")
    kapt("androidx.room:room-compiler:2.7.2")


    // Hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    kapt("com.google.dagger:hilt-android-compiler:2.56.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // KotlinX Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Compose Material (for legacy widgets)
    implementation("androidx.compose.material:material")

    // 测试
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Supabase 3.2.0 & ktor-android 3.2.0
    implementation(platform("io.github.jan-tennert.supabase:bom:3.2.0"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")
    implementation("io.github.jan-tennert.supabase:storage-kt")
    // ------------------ Ktor 客户端（Android HTTP）依赖------------------
    implementation("io.ktor:ktor-client-android:3.2.1")
    implementation("io.ktor:ktor-client-logging:3.2.1") // 日志
    implementation("io.ktor:ktor-client-content-negotiation:3.2.1") // Content Negotiation
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.1") // JSON 序列化
    implementation("io.ktor:ktor-client-auth:3.2.1") // 认证（Supabase JWT）
    // ------------------ Gson / Okio 等工具库（如用到）------------------
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okio:okio:3.7.0")
    // 你自己的其它三方库（比如 coil），完全不用删
}
