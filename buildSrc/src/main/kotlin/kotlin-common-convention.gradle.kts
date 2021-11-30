plugins {
    `java-library`
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
    implementation("io.arrow-kt:arrow-core:1.0.1")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}