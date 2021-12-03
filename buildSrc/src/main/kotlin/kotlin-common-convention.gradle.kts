plugins {
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
    implementation("io.arrow-kt:arrow-core:1.0.1")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0-RC")
    testImplementation("io.kotest:kotest-runner-junit5:5.0.1")
    testImplementation("io.kotest:kotest-assertions-core:5.0.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
