package org.example.delivery

import arrow.core.flatMap
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.get
import org.example.gateway.TheoriesGateway
import org.example.usecases.TheoriesUseCases
import org.example.usecases.execute
import org.example.utils.either.mapT

fun interface Controller {
    fun routes(): Router

    companion object {

        @JvmStatic
        fun of(vertx: Vertx, theoriesGateway: TheoriesGateway): Controller = Controller {
            Router.router(vertx).apply {
                get("/theories").coroutineHandlerEither {
                    TheoriesUseCases(theoriesGateway).makeGetAllTheoriesIndex.execute()
                        .mapT { (name, version) ->
                            "/theories/$name/versions/$version"
                        }
                }

                post("/theories")
                    .handler(BodyHandler.create())
                    .coroutineHandlerEither(successCode = HTTPStatusCode.CREATED) {
                        TheoriesUseCases(theoriesGateway).makeCreateTheory
                            .execute(it.bodyAsJson["name"], it.bodyAsJson["value"])
                    }

                get("/theories/:name").coroutineHandlerEither {
                    TheoriesUseCases(theoriesGateway)
                        .makeGetTheoryByName.execute(it.pathParam("name"))
                }

                patch("/theories/:name")
                    .handler(BodyHandler.create())
                    .coroutineHandlerEither {
                        TheoriesUseCases(theoriesGateway).makeUpdateTheory
                            .execute(it.pathParam("name"), it.bodyAsJson["value"])
                    }

                get("/theories/:name/versions").coroutineHandlerEither { ctx ->
                    TheoriesUseCases(theoriesGateway).makeGetAllTheoriesIndexByName
                        .execute(ctx.pathParam("name"))
                        .mapT { (name, version) ->
                            "/theories/$name/versions/$version"
                        }
                }

                get("/theories/:name/versions/:version").coroutineHandlerEither { ctx ->
                    ctx.pathParam("version").parseInt()
                        .flatMap {
                            TheoriesUseCases(theoriesGateway)
                                .makeGetTheoryByNameAndVersion.execute(ctx.pathParam("name"), it)
                        }
                }

                delete("/theories/:name").coroutineHandlerEither { ctx ->
                    TheoriesUseCases(theoriesGateway)
                        .makeDeleteTheory
                        .execute(ctx.pathParam("name"))
                }
            }
        }

    }

}
