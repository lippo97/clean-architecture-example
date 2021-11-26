package org.example.delivery

import io.vertx.core.Vertx
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import it.unibo.tuprolog.serialize.MimeType
import it.unibo.tuprolog.serialize.TheorySerializer
import org.example.GetTheoriesUseCase
import org.example.gateway.TheoriesGateway

fun interface Controller {
    fun routes(): Router

    companion object {

        @JvmStatic
        fun of(vertx: Vertx, theoriesGateway: TheoriesGateway): Controller = Controller {
            Router.router(vertx).apply {
                get("/theories").handler { ctx ->
                    // val role = ctx.getHeaders()['Authentication']['role']
                    // permissionManager(role, CONFIGURATOR) {
                    //   val theories = GetTheoriesUseCase(theoriesGateway).getTheories()
                    //   ctx.response()
                    //      .end(TheorySerializer.of(MimeType.Json).serializeMany(theories))
                    // }
                    val theories = GetTheoriesUseCase(theoriesGateway).getTheories()
                    ctx.response()
                        .end(TheorySerializer.of(MimeType.Json).serializeMany(theories))
                }

                get("/theories/ws").handler { ctx ->
                    val theories = GetTheoriesUseCase(theoriesGateway).getTheories()
                    vertx.createHttpServer().webSocketHandler()
                }

                post("/theories").handler { ctx ->
                    ctx.parsedHeaders()
                }
            }
        }

    }
}