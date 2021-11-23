plugins {
    kotlin("jvm") version "1.6.0"
    `java-library`
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }
}
