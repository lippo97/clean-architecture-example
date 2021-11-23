plugins {
    `kotlin-common-convention`
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation("io.vertx:vertx-core:4.2.1")
    implementation("io.vertx:vertx-lang-kotlin:4.2.1")
    implementation("io.vertx:vertx-web:4.2.1")

    runtimeOnly("com.fasterxml.jackson.core:jackson-core:2.10.1")
    runtimeOnly("com.fasterxml.jackson.core:jackson-annotations:2.10.1")
    runtimeOnly("com.fasterxml.jackson.core:jackson-databind:2.10.1")
}
