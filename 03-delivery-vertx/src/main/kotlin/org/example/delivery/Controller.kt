package org.example.delivery

import io.vertx.ext.web.Router
import org.example.delivery.handlers.theoriesRoutes

fun interface Controller {
    fun routes(): Router

    companion object {
        fun DependencyGraph.makeController(): Controller = Controller {
            Router.router(this.vertx)
                .apply(::theoriesRoutes)
                .apply(::theoriesRoutes)
                .apply(::theoriesRoutes)
        }
    }
}
