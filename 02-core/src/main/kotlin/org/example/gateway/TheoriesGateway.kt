package org.example.gateway

import it.unibo.tuprolog.theory.Theory

interface TheoriesGateway {
    fun getTheories(): List<Theory>
}
