plugins {
    `kotlin-common-convention`
}

dependencies {
    implementation(project(":01-domain"))
    implementation(project(":02-core"))
    implementation("io.arrow-kt:arrow-core:1.0.1")
}
