package org.example.delivery

import io.vertx.ext.web.Router
import io.vertx.core.json.Json
import org.example.delivery.json.TheoryEncoder
import org.example.gateway.TheoriesGateway

fun interface Controller {
    fun routes(): Router

    companion object {
        fun of(router: Router, theoriesGateway: TheoriesGateway): Controller = Controller {
            router.get("/theories").handler { ctx ->
                val theories = theoriesGateway.getTheories()
                ctx.response()
                    .end(TheoryEncoder.encode(theories))
            }
            router
        }

    }
}