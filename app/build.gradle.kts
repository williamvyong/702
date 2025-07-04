plugins {
    id("com.google.dagger.hilt.android")
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.williamv.debtmake"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.williamv.debtmake"
        minSdk = 24
        targetSdk = 34
        // …
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
    }
}

dependencies {
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.10.00")) // <── 换成一个可用的版本
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // 如果需要 Material Icons
    implementation("androidx.compose.material:material-icons-core:1.5.0")
    implementation("androidx.compose.material:material-icons-extended:1.5.0")
    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.7.0")       // <── 必加

    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    // ViewModel + Hilt + Navigation 也要同步版本
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    // Supabase Kotlin (Core + GoTrue + Realtime + Postgrest)

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // 添加 Accompanist Pager
    implementation("com.google.accompanist:accompanist-pager:0.30.1")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.30.1")
    // ✅ 添加 Room 的核心库
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    // 可选协程扩展
    // ✅ 单元测试
    testImplementation("junit:junit:4.13.2")
}