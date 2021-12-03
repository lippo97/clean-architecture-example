plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jlleitschuh.gradle:ktlint-gradle:10.2.0")
    implementation(kotlin("gradle-plugin", "1.6.0"))
}