plugins {
    alias(libs.plugins.android.application)
    // Google Service Gradle Plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.jasdeep.finalproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jasdeep.finalproject"
        minSdk = 33
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    // Firebase Analytics
    implementation(libs.firebase.analytics)
    // Firebase Authentication
    implementation(libs.firebase.auth)
    // Firebase Realtime Database
    implementation(libs.firebase.database)

    // Coil for image loading
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    implementation("com.basgeekball:awesome-validation:4.3")
}