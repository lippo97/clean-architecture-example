package org.example.delivery

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import org.example.Dependencies
import org.example.delivery.handlers.theoriesRoutes

fun interface Controller {
    fun routes(): Router

    companion object {

        @JvmStatic
        fun of(vertx: Vertx, dependencies: Dependencies): Controller = Controller {
            Router.router(vertx).apply {
                dependencies.theoriesRoutes(this)
            }
        }

    }

}
