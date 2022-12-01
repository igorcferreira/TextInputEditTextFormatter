plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.dokka")
    id("maven-publish")
}

val bundleId: String by project
val sdkArtifactId: String by project

android {
    namespace = "$bundleId.compose"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

val javadocJar by tasks.registering(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Bundles the documentation in 'javadoc' format into a jar file"
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

artifacts {
    archives(javadocJar)
}

afterEvaluate {
    val version: String by project
    val group: String by project

    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components.getByName("release"))
                groupId = group
                artifactId = "$sdkArtifactId-compose"
                this.version = version

                artifact(javadocJar)

                pom {
                    name.set("TextInputEditTextFormatter Compose")
                    licenses {
                        license {
                            name.set("Apache-2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }
                    developers {
                        developer {
                            id.set("igorcferreira")
                            name.set("Igor Ferreira")
                        }
                    }
                }
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:[1.9,2.0[")
    implementation("androidx.compose.foundation:foundation:[1.3,1.4[")

    implementation(project(":textinputedittextformatter"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}