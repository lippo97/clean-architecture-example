package org.example.delivery

import io.vertx.core.Vertx
import org.example.gateway.TheoriesGateway

class DependencyGraph(
    val vertx: Vertx,
    theoriesGateway: TheoriesGateway,
) : TheoriesGateway by theoriesGateway {
    companion object
}
