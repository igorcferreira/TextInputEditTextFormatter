plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
}

val bundleId: String by project
val sdkArtifactId: String by project

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
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
                from(components.getByName("java"))
                groupId = group
                artifactId = sdkArtifactId
                this.version = version

                artifact(javadocJar)

                pom {
                    name.set("TextInputEditTextFormatter")
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
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.2.0")
    testImplementation("org.mockito:mockito-inline:4.2.0")
}