package org.example.delivery.handlers

import arrow.core.flatMap
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.get
import org.example.delivery.HTTPStatusCode
import org.example.delivery.coroutineHandlerEither
import org.example.delivery.parseInt
import org.example.gateway.TheoriesGateway
import org.example.usecases.createTheoryUseCase
import org.example.usecases.deleteTheoryUseCase
import org.example.usecases.getAllTheoriesIndexUseCase
import org.example.usecases.getTheoryByNameAndVersionUseCase
import org.example.usecases.getTheoryByNameUseCase
import org.example.usecases.updateTheoryUseCase
import org.example.utils.either.mapT

fun TheoriesGateway.theoriesRoutes(router: Router) {
    router.apply {
        get("/theories").coroutineHandlerEither {
            this@theoriesRoutes.getAllTheoriesIndexUseCase()
                .execute()
                .mapT { (name, version) ->
                    "/theories/$name/versions/$version"
                }
        }
        post("/theories")
            .handler(BodyHandler.create())
            .coroutineHandlerEither(successCode = HTTPStatusCode.CREATED) {
                this@theoriesRoutes
                    .createTheoryUseCase(it.bodyAsJson["name"], it.bodyAsJson["value"])
                    .execute()
            }
        get("/theories/:name").coroutineHandlerEither {
            this@theoriesRoutes
                .getTheoryByNameUseCase(it.pathParam("name")).execute()
        }

        patch("/theories/:name")
            .handler(BodyHandler.create())
            .coroutineHandlerEither {
                this@theoriesRoutes
                    .updateTheoryUseCase(it.pathParam("name"), it.bodyAsJson["value"])
                    .execute()
            }

        get("/theories/:name/versions").coroutineHandlerEither { ctx ->
            this@theoriesRoutes
                .getAllTheoriesIndexUseCase(ctx.pathParam("name"))
                .execute()
                .mapT { (name, version) ->
                    "/theories/$name/versions/$version"
                }
        }

        get("/theories/:name/versions/:version").coroutineHandlerEither { ctx ->
            ctx.pathParam("version").parseInt()
                .flatMap {
                    this@theoriesRoutes
                        .getTheoryByNameAndVersionUseCase(ctx.pathParam("name"), it)
                        .execute()
                }
        }

        delete("/theories/:name").coroutineHandlerEither { ctx ->
            this@theoriesRoutes
                .deleteTheoryUseCase(ctx.pathParam("name"))
                .execute()
        }
    }
}
