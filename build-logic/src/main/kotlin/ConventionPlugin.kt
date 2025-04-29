@file:Suppress("DEPRECATION")

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class ConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.withId(ANDROID_APPLICATION_LIB) {
            configureAppExtension(
                androidExtension = project.extensions.getByType(AppExtension::class.java),
                project = project
            )
        }

        project.plugins.withId(ANDROID_LIB) {
            configureLibraryExtension(project.extensions.getByType(LibraryExtension::class.java))
        }

        configureKotlinCompile(project)
    }

    private fun configureAppExtension(androidExtension: AppExtension, project: Project) {
        androidExtension.apply {
            compileSdkVersion(TARGET_SDK)
            configureDefaultConfig(project)
            flavorDimensions(DIMENSION_FLAVORS)
            configureProductFlavors()
            configureSigningConfigs(project)
            configureBuildTypes()
            configurePackagingOptions()
        }
    }

    private fun AppExtension.configureDefaultConfig(project: Project) {
        defaultConfig {
            applicationId = APPLICATION_ID
            minSdk = MIN_SDK
            targetSdk = TARGET_SDK
            versionCode = project.getProperty("VERSION_CODE").toInt()
            versionName = project.getProperty("VERSION_NAME")
        }
    }

    private fun AppExtension.configureProductFlavors() {
        productFlavors {
            create(DEV_FLAVOR) {
                dimension = DIMENSION_FLAVORS
            }
            create(PROD_FLAVOR) {
                dimension = DIMENSION_FLAVORS
            }
        }
    }

    private fun AppExtension.configureSigningConfigs(project: Project) {
        val projectPath = project.file("/build-logic/debug.keystore")
        println("Project path: $projectPath")
        signingConfigs {
            create(RELEASE_CONFIG) {
                // TODO create configuration for releaseConfig
                storeFile = project.file("../build-logic/debug.keystore")
                storePassword = "android"
                keyAlias = "androiddebugkey"
                keyPassword = "android"
            }
            create(DEBUG_CONFIG) {
                storeFile = project.file("../build-logic/debug.keystore")
                storePassword = "android"
                keyAlias = "androiddebugkey"
                keyPassword = "android"
            }
        }
    }

    private fun AppExtension.configureBuildTypes() {
        buildTypes {
            getByName(RELEASE_TYPE) {
                signingConfig = signingConfigs.getByName(RELEASE_CONFIG)
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile(PROGUARD_ANDROID_TXT),
                    PROGUARD_RULES
                )
            }
            create(QA_TYPE){
                signingConfig = signingConfigs.getByName(DEBUG_CONFIG)
                isDebuggable = true
            }
            getByName(DEBUG_TYPE) {
                isDebuggable = true
            }
        }
    }

    private fun AppExtension.configurePackagingOptions() {
        packagingOptions {
            exclude("META-INF/*.kotlin_module")
            exclude("META-INF/AL2.0")
            exclude("META-INF/LGPL2.1")
        }
    }

    private fun configureLibraryExtension(androidExtension: LibraryExtension) {
        androidExtension.apply {
            compileSdk = TARGET_SDK
            configureDefaultConfig()
            configureBuildTypes()
        }
    }

    private fun LibraryExtension.configureDefaultConfig() {
        defaultConfig {
            minSdk = MIN_SDK
            targetSdk = TARGET_SDK
        }
    }

    private fun LibraryExtension.configureBuildTypes() {
        buildTypes {
            getByName(RELEASE_TYPE) {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile(PROGUARD_ANDROID_TXT),
                    PROGUARD_RULES
                )
            }
        }
    }

    private fun configureKotlinCompile(project: Project) {
        project.tasks.withType(KotlinCompile::class.java).configureEach {
            kotlinOptions {
                jvmTarget = JVM_TARGET
            }
        }
    }

    private fun Project.getProperty(propertyName: String): String {
        return findProperty(propertyName) as? String
            ?: throw IllegalStateException("Property '$propertyName' not found in gradle.properties")
    }

    companion object {
        private const val APPLICATION_ID = "com.degrin.bitcoinwallet"
        private const val ANDROID_APPLICATION_LIB = "com.android.application"
        private const val ANDROID_LIB = "com.android.library"
        private const val MIN_SDK = 21
        private const val TARGET_SDK = 35
        private const val JVM_TARGET = "1.8"

        private const val RELEASE_TYPE = "release"
        private const val RELEASE_CONFIG = "releaseConfig"
        private const val DEBUG_TYPE = "debug"
        private const val QA_TYPE = "qa"
        private const val DEBUG_CONFIG = "debugConfig"

        private const val DEV_FLAVOR = "dev"
        private const val PROD_FLAVOR = "prod"
        private const val DIMENSION_FLAVORS = "flavor"

        private const val PROGUARD_ANDROID_TXT = "proguard-android-optimize.txt"
        private const val PROGUARD_RULES = "proguard-rules.pro"
    }
}
