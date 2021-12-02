package org.example.main

import io.vertx.core.Vertx
import org.example.delivery.Controller.Companion.makeController
import org.example.delivery.DependencyGraph
import org.example.main.delivery.make


fun main() {
    val vertx = Vertx.vertx()
    val controller = with(DependencyGraph.make(vertx)) { makeController() }

    vertx
        .createHttpServer()
        .requestHandler(controller.routes())
        .listen(8080).onComplete {
            println("Running...")
        }
}