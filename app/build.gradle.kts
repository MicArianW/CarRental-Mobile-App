plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"   // Required for Kotlin 2.0
}

android {
    namespace = "com.example.carrental"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.carrental"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        // MUST match Kotlin 2.0 (Compose Compiler)
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {

    // ===============================
    // COMPOSE + MATERIAL3
    // ===============================
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // 🚨 IMPORTANT: RESTORES ALL OLD ICONS (DirectionsCar, Login, Facebook, Visibility, etc.)
    implementation("androidx.compose.material:material-icons-extended")

    // ===============================
    // NAVIGATION
    // ===============================
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ===============================
    // IMAGE LOADING
    // ===============================
    implementation("io.coil-kt:coil-compose:2.6.0")

    // ===============================
    // VIEWMODEL
    // ===============================
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // ===============================
    // FIREBASE
    // ===============================
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage")

    // ===============================
    // GOOGLE MAPS
    // ===============================
    implementation("com.google.maps.android:maps-compose:2.11.4")
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    // ===============================
    // ROOM DATABASE
    // ===============================
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // ===============================
    // CORE + ACTIVITY
    // ===============================
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.core:core-ktx:1.12.0")

    // ===============================
    // DEBUG TOOLS
    // ===============================
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // ===============================
    // TESTING
    // ===============================
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.ext:junit:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0")
    testImplementation("junit:junit:4.13.2")
}



