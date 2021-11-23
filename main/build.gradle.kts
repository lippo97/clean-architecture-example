plugins {
    `kotlin-common-convention`
    application
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":delivery"))
    implementation(project(":gateway-in-memory"))

    implementation("io.vertx:vertx-core:4.2.1")
    implementation("io.vertx:vertx-lang-kotlin:4.2.1")
    implementation("io.vertx:vertx-web:4.2.1")


}

application {
    mainClass.set("org.example.main.MainKt")
}