package org.example.main

import io.vertx.core.Vertx
import org.example.delivery.Controller
import org.example.gateway.InMemoryDatabase
import org.example.gateway.InMemoryTheoriesGateway

fun main() {
    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()
    val controller = Controller.of(
        vertx = vertx,
        theoriesGateway = InMemoryTheoriesGateway(InMemoryDatabase())
    )

    server.requestHandler(controller.routes())
        .listen(8080).onComplete {
            println("Running...")
        }
}