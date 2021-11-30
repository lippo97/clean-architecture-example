plugins {
    `kotlin-common-convention`
    application
}

dependencies {
    implementation(project(":01-domain"))
    implementation(project(":02-core"))
    implementation(project(":03-gateway-inmemory"))
    implementation(project(":03-delivery-vertx"))

    implementation("io.vertx:vertx-core:4.2.1")
    implementation("io.vertx:vertx-lang-kotlin:4.2.1")
    implementation("io.vertx:vertx-web:4.2.1")

    testImplementation("io.vertx:vertx-lang-kotlin:4.2.1")
    testImplementation("io.vertx:vertx-lang-kotlin-coroutines:4.2.1")
}

application {
    mainClass.set("org.example.main.MainKt")
}