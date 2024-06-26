plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.22"
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.zishan.paypaycurrencyconversion"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zishan.paypaycurrencyconversion"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        buildTypes {
            debug {
                buildConfigField(
                    "String",
                    "EXCHANGE_APP_ID",
                    "${rootProject.properties["EXCHANGE_APP_ID"]}"
                )
            }
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                buildConfigField(
                    "String",
                    "EXCHANGE_APP_ID",
                    "${rootProject.properties["EXCHANGE_APP_ID"]}"
                )
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }

        buildFeatures {
            viewBinding = true
            buildConfig = true
        }
    }

    dependencies {

        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.cardview:cardview:1.0.0")

        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

        implementation("com.google.dagger:dagger:2.51")
        kapt("com.google.dagger:dagger-compiler:2.51")
        annotationProcessor("com.google.dagger:dagger-compiler:2.51")

        implementation("androidx.room:room-ktx:2.6.1")
        kapt("androidx.room:room-compiler:2.6.1")
        implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        testImplementation ("androidx.arch.core:core-testing:2.2.0")
        testImplementation ("io.mockk:mockk:1.13.10")
        testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    }
}