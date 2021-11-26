plugins {
  `kotlin-common-convention`
}

dependencies {
  implementation(project(":01-domain"))
  api("it.unibo.tuprolog:serialize-core-jvm:0.20.4")
  api("it.unibo.tuprolog:serialize-theory-jvm:0.20.4")

  runtimeOnly("com.fasterxml.jackson.core:jackson-core:2.13.0")
  runtimeOnly("com.fasterxml.jackson.core:jackson-annotations:2.13.0")
  runtimeOnly("com.fasterxml.jackson.core:jackson-databind:2.13.0")
}
