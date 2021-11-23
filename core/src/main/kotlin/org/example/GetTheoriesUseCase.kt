package org.example

import org.example.gateway.TheoriesGateway

class GetTheoriesUseCase(theoriesGateway: TheoriesGateway) {
    fun getTheories(): List<Theory> {
        return emptyList()
    }
}