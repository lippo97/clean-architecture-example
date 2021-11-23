package org.example.main

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import org.example.delivery.Controller
import org.example.gateway.InMemoryDatabase
import org.example.gateway.InMemoryTheoriesGateway

fun main() {
    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()
    val router = Controller.of(
        router = Router.router(vertx),
        theoriesGateway = InMemoryTheoriesGateway(InMemoryDatabase())
    )

    server.requestHandler(router.routes()).listen(8080).onComplete {
        println("Running...")
    }
}