// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

buildscript {
    repositories {
        mavenCentral()
        javaClass
        google()
        gradlePluginPortal()
        maven { url = uri("https://storage.googleapis.com/r8-releases/raw") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }

    dependencies {
        classpath(libs.kotlinSerializationGradle)
        classpath(":build-logic")
    }
}