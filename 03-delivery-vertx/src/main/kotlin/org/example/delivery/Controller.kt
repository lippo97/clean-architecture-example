package org.example.delivery

import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.get
import org.example.entities.Theory
import org.example.gateway.TheoriesGateway
import org.example.usecases.TheoriesUseCases

fun interface Controller {
    fun routes(): Router

    companion object {

        @JvmStatic
        fun of(vertx: Vertx, theoriesGateway: TheoriesGateway): Controller = Controller {
            val theoriesUseCase = TheoriesUseCases(theoriesGateway)
            Router.router(vertx).apply {
                get("/theories").handler { ctx ->
                    val theories = theoriesUseCase.makeGetAllTheories.execute(Unit)
                    theories.fold(
                        ifLeft = {

                        },
                        ifRight = {
                            ctx.response()
                                .end(Json.encodePrettily(it))
                        }
                    )

                }

                get("/theories/:name").handler { ctx ->
                    val theoryName = ctx.pathParam("name")
                    val theories = theoriesUseCase.makeGetTheoryByName.execute(theoryName)
                    ctx.response()
                        .end(Json.encodePrettily(theories))
                }

                post("/theories")
                    .handler(BodyHandler.create())
                    .handler { ctx ->
                        val body = ctx.bodyAsJson
                        val result = theoriesUseCase.makeCreateTheory.execute(
                            Theory(
                                name = body["name"],
                                value = body["value"]
                            )
                        )
                        ctx.response()
                            .end(Json.encodePrettily(result))
                    }
            }
        }

    }
}