package org.example.main

import io.vertx.core.Vertx
import org.example.delivery.DependencyGraph
import org.example.gateway.InMemoryDatabase
import org.example.gateway.InMemoryTheoriesGateway

fun DependencyGraph.Companion.make(vertx: Vertx): DependencyGraph =
    DependencyGraph(
        vertx = vertx,
        theoriesGateway = InMemoryTheoriesGateway(InMemoryDatabase())
    )
