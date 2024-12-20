// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.navigation.safeargs) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.app.distribution) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}