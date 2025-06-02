plugins {
    kotlin("plugin.serialization") version "1.9.0" // Use your Kotlin version

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("kotlin-parcelize")
    id("kotlin-kapt")      // If using Room or other annotation processors
}

android {
    namespace = "com.example.feedsnap"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.feedsnap"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

//dependencies {
//    implementation("io.coil-kt:coil-compose:2.2.2")
//    implementation("androidx.navigation:navigation-compose:2.7.5")
//
//    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
//    implementation(libs.androidx.camera.core)
//    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
//
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
////    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
//    implementation("androidx.navigation:navigation-compose:2.7.5")
//    implementation("androidx.compose.material3:material3:1.1.2")
//    implementation("androidx.compose.foundation:foundation:1.5.4")
//
//    // Accompanist Permissions
//    implementation("com.google.accompanist:accompanist-permissions:0.31.5-beta")
//
//    // CameraX
//    implementation("androidx.camera:camera-core:1.3.1")
//    implementation("androidx.camera:camera-camera2:1.3.1")
//    implementation("androidx.camera:camera-lifecycle:1.3.1")
//    implementation("androidx.camera:camera-view:1.3.1")
//    implementation("androidx.camera:camera-extensions:1.3.1")
//
//    implementation("androidx.compose.material:material-icons-core:1.5.4")
//    implementation("androidx.compose.material:material-icons-extended:1.5.4")
//
//    // Retrofit Networking
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
//
//    // Database
//    implementation("androidx.room:room-runtime:2.6.0")
////    kapt("androidx.room:room-compiler:2.6.0")
////    implementation(kapt("androidx.room:room-compiler:2.6.0"))
//    implementation("androidx.room:room-ktx:2.6.0")
//
//    // Charts
//    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
//
//    // Dependency Injection
////    implementation("io.insert-koin:koin-android:3.5.0")
////    implementation("io.insert-koin:koin-androidx-compose:3.5.0")
//    implementation(libs.androidx.navigation.safe.args.generator)
//
//    // Testing
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
////    debugImplementation("androidx.compose.ui:ui-tooling")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
//
//    // Kotlin Serialization
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
//
//    // Retrofit with Kotlin Serialization Converter
//    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
//
//    // Koin Core (required)
//    implementation("io.insert-koin:koin-core:3.5.3")
//    implementation("io.insert-koin:koin-android:3.5.3")
//    implementation("io.insert-koin:koin-androidx-compose:3.5.3")
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//}

dependencies {
    // Core (use version catalog consistently)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM - use ONE BOM declaration
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3") // Removed duplicate
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.navigation:navigation-compose:2.7.5") // Single declaration
    implementation("androidx.compose.foundation:foundation")

    // Accompanist
    implementation("com.google.accompanist:accompanist-permissions:0.31.5-beta")

    // CameraX (removed libs.androidx.camera.core)
    implementation("androidx.camera:camera-core:1.3.1")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    implementation("androidx.camera:camera-extensions:1.3.1")

    // Icons (use BOM version)
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Database - FIXED kapt
    implementation("androidx.room:room-runtime:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0") // Corrected
    implementation("androidx.room:room-ktx:2.6.0")

    // Charts
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Koin (use consistent versions)
    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-android:3.5.3")
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2") // Moved here

    // Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // REMOVED: libs.androidx.navigation.safe.args.generator (not a dependency)
}