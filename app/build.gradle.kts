import org.apache.tools.ant.property.LocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.example.rickandmorty"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rickandmorty"
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
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/\"")
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.foundation)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.material.icons.extended)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)
    testImplementation(libs.room.testing)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)


    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit5.api)
    testImplementation(libs.assertk)
    testImplementation(libs.turbine)
    testImplementation(libs.junit5.params)
    testRuntimeOnly(libs.junit5.engine)

    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.androidx.core.testing)

    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)

}

tasks.withType<Test>{
    useJUnitPlatform()
}