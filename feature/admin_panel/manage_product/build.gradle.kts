import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "manage_product"
            isStatic = true
        }
    }

    sourceSets {
        // respective client ktor dependencies for platform specific
        androidMain.dependencies {
            implementation(libs.ktor.android.client)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin.client)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Nested Navigation
            implementation(libs.compose.navigation)
            // message bar kmp
            implementation(libs.messagebar.kmp)

            implementation(project(path = ":shared"))
            implementation(project(path = ":data"))

            //adding coil compose for image loading
            implementation(libs.coil3)
            implementation(libs.coil3.compose)
            implementation(libs.coil3.compose.core)
            implementation(libs.coil3.network.ktor)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.atulit.nutrisport.manage_product"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    lint {
        // Disables the given check in release builds
        disable.add("HardcodedText")
        // Or ignore it completely
        // ignore.add("HardcodedText")
        disable.add("NullSafeMutableLiveData")

        // Or set its severity to warning instead of error
        // warning.add("HardcodedText")

        // Check all issues (including disabled ones) but don't abort the build
        checkAllWarnings = true
        // Abort build on error (true by default for release builds)
        abortOnError = true
        // Write output to a file
        htmlOutput = file("lint-report.html")
    }
}


