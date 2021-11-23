package org.example

import org.example.gateway.TheoriesGateway

class GetTheoriesUseCase(private val theoriesGateway: TheoriesGateway) {
    fun getTheories(): List<Theory> = theoriesGateway.getTheories()
}
