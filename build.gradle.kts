plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.20"
    id("org.jetbrains.intellij") version "1.16.0"
}

group = "one.platform.plugin"
version = "202"

repositories {
    maven {url = uri("https://maven.aliyun.com/repository/central/")}
    maven {url = uri("https://maven.aliyun.com/repository/public/")}
    maven {url = uri("https://maven.aliyun.com/repository/google/")}
    maven {url = uri("https://maven.aliyun.com/repository/jcenter/")}
    maven {url = uri("https://maven.aliyun.com/repository/gradle-plugin")}
    google()
    mavenCentral()

}

dependencies {
    // implementation("org.jdom:jdom:1.1.3")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.1.5")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("202")
        untilBuild.set("231.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
