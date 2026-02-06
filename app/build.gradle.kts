plugins {
    alias(libs.plugins.android.application)
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("D:\\IDE\\AndroidStudio\\KeyStore\\myKeyStore.jks")
            storePassword = "197319731973"
            keyAlias = "androidrelease"
            keyPassword = "197319731973"
        }
    }
    namespace = "com.anrola.adclicker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.anrola.adclicker"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false

            applicationVariants.all {
                outputs.forEach { output ->
                    val appName = "ADclicker"
                    val versionName = this.versionName
                    val buildType = this.buildType.name
                    val newFileName = "${appName}_v${versionName}_${buildType}.apk"
                    (output as com.android.build.gradle.internal.api.ApkVariantOutputImpl).outputFileName =
                        newFileName
                }
            }
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
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}