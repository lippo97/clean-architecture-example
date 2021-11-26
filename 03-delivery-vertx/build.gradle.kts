plugins {
    `kotlin-common-convention`
}

dependencies {
    implementation(project(":01-domain"))
    implementation(project(":02-core"))
    implementation(project(":02-serialization"))
    implementation("io.vertx:vertx-core:4.2.1")
    implementation("io.vertx:vertx-lang-kotlin:4.2.1")
    implementation("io.vertx:vertx-web:4.2.1")
}
