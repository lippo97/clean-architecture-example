package org.example.delivery

import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.get
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.example.gateway.TheoriesGateway
import org.example.usecases.TheoriesUseCases
import org.example.usecases.execute

fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit) {
    handler { ctx ->
        GlobalScope.launch(ctx.vertx().dispatcher()) {
            kotlin
                .runCatching { fn(ctx) }
                .onFailure(ctx::fail)
        }
    }
}

fun interface Controller {
    fun routes(): Router

    companion object {

        @JvmStatic
        fun of(vertx: Vertx, theoriesGateway: TheoriesGateway): Controller = Controller {
            val theoriesUseCase = TheoriesUseCases(theoriesGateway)
            Router.router(vertx).apply {
                get("/theories").coroutineHandler { ctx ->
                    val theories = theoriesUseCase.makeGetAllTheories.execute()
                    theories.fold(
                        ifLeft = {
                            throw IllegalStateException()
                        },
                        ifRight = {
                            ctx.response()
                                .end(Json.encodePrettily(it))
                        }
                    )
                }

                get("/theories/:name").coroutineHandler { ctx ->
                    val theoryName = ctx.pathParam("name")
                    val theory = theoriesUseCase.makeGetTheoryByName.execute(theoryName)
                    theory.fold(
                        {
                            ctx.response().apply {
                                statusCode = 404
                                statusMessage = it.msg
                            }.end()
                        },
                        {
                            ctx.response()
                                .end(Json.encodePrettily(it))
                        }
                    )
                }

                post("/theories")
                    .handler(BodyHandler.create())
                    .coroutineHandler { ctx ->
                        val body = ctx.bodyAsJson
                        val theory = theoriesUseCase.makeCreateTheory.execute(body["name"], body["value"])
                        theory.fold(
                            {
                                ctx.fail(500)
                            },
                            {
                                ctx.response()
                                    .end(Json.encodePrettily(it))
                            }
                        )
                    }
            }
        }

    }
}