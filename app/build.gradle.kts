plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "me.newbly.camyomi"
    compileSdk = 34

    defaultConfig {
        applicationId = "me.newbly.camyomi"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // default value: androidx.test.runner.AndroidJUnitRunner
        testInstrumentationRunner = "me.newbly.camyomi.CustomTestRunner"

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.camera2)
    implementation(libs.camera2.lifecycle)
    implementation(libs.camera2.video)
    implementation(libs.camera2.view)
    implementation(libs.camera2.mlkit.vision)
    implementation(libs.camera2.extensions)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.dynamic.features.fragment)
    implementation(libs.text.recognition.japanese)
    implementation(libs.hilt)
    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.room)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    implementation(libs.kuromoji)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.kotlinx.coroutines.play.services)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.robolectric)
    testImplementation(libs.hilt.testing)
    testImplementation(libs.room.test)
    kspTest(libs.hilt.compiler)
    kspTest(libs.room.compiler)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.hilt.testing)
    androidTestImplementation(libs.room.test)
    androidTestImplementation(libs.navigation.testing)
    kspAndroidTest(libs.hilt.compiler)
    kspAndroidTest(libs.room.compiler)
}