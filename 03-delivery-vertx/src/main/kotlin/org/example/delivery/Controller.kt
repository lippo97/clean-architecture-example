package org.example.delivery

import io.vertx.ext.web.Router
import io.vertx.core.json.Json
import org.example.GetTheoriesUseCase
import org.example.delivery.json.TheoryEncoder
import org.example.gateway.TheoriesGateway

typealias MIMEType = String;
typealias Encoder = String;
typealias Encodings = Map<MIMEType, Encoder>

fun interface Controller {
    fun routes(): Router

    companion object {
        fun of(router: Router, theoriesGateway: TheoriesGateway): Controller = Controller {
            router.get("/theories").handler { ctx ->
                val theories = GetTheoriesUseCase(theoriesGateway).getTheories()
                ctx.response()
                    .end(TheoryEncoder.encode(theories))
            }
            router
        }

    }
}