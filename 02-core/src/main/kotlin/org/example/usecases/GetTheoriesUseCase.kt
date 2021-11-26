package org.example.usecases

import it.unibo.tuprolog.theory.Theory
import org.example.gateway.TheoriesGateway

class GetTheoriesUseCase(private val theoriesGateway: TheoriesGateway) {
    fun getTheories(): List<Theory> = theoriesGateway.getTheories()
}