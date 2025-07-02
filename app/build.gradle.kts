plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}


android {
    namespace = "com.williamv.debtmake"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.williamv.debtmake"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true                      // ← 一定要打开
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Compose BOM，管理各 Compose 库版本
    val composeBom = platform("androidx.compose:compose-bom:2024.03.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Compose UI 基础
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3") // Material 3 UI
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")          // 预览支持
    debugImplementation("androidx.compose.ui:ui-tooling")            // Compose 工具（Debug）
    debugImplementation("androidx.compose.ui:ui-test-manifest")      // UI Test Manifest
    implementation("androidx.compose.material:material:1.5.0")

    // Activity 扩展
    implementation("androidx.activity:activity-compose:1.8.0")

    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.7.0")

    // Lifecycle 与 ViewModel for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Hilt DI
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")

    // Kotlin 协程（如果你在 Compose 中直接用 rememberCoroutineScope() 并 launch，需要）
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Core KTX
    implementation("androidx.core:core-ktx:1.10.1")
}