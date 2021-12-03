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

    // This is deprecated
    // see https://stackoverflow.com/questions/5644011/multi-project-test-dependencies-with-gradle/60138176#60138176
    testImplementation(project(":03-delivery-vertx").dependencyProject.sourceSets.test.get().output)
    testImplementation("io.vertx:vertx-lang-kotlin:4.2.1")
    testImplementation("io.vertx:vertx-lang-kotlin-coroutines:4.2.1")
}

application {
    mainClass.set("org.example.main.MainKt")
}
