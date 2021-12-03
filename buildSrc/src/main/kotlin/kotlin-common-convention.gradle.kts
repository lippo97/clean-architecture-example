import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
    implementation("io.arrow-kt:arrow-core:1.0.1")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0-RC")
    testImplementation("io.kotest:kotest-runner-junit5:5.0.1")
    testImplementation("io.kotest:kotest-assertions-core:5.0.1")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
