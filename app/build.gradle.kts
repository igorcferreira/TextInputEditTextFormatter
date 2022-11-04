plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val bundleId: String by project
val buildNumber: String by project
val version: String by project

android {
    namespace = "dev.igorcferreira.textinputedittextformatter.app"
    compileSdk = 33

    defaultConfig {
        applicationId = "$bundleId.app"
        minSdk = 24
        targetSdk = 33
        versionCode = buildNumber.toInt()
        versionName = version

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

    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.compose.material:material:1.3.0")

    implementation(project(":textinputedittextformatter"))
    implementation(project(":textinputedittextformatter-android"))
    implementation(project(":textinputedittextformatter-compose"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}