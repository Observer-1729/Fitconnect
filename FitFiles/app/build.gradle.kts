plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.gms.google-services")
    id("kotlin-kapt")

    id ("kotlinx-serialization")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.fitconnect"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fitconnect"
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

dependencies {
    //For getting extra icons
    implementation(libs.androidx.material.icons.extended.android)


// WorkManager (for background tasks)
    implementation(libs.androidx.work.runtime.ktx)

// Room (for local database)
    implementation (libs.androidx.room.runtime)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.bom)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)



    //viewmodel and navigation
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.compose)

    //firebase dependencies
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("androidx.compose.material:material:1.5.0")

    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("io.coil-kt:coil-gif:2.5.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.28.0")  // Use the latest version


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}