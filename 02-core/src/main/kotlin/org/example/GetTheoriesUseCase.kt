package org.example

import it.unibo.tuprolog.theory.Theory
import org.example.gateway.TheoriesGateway

/*
 * Gerarchia di ruoli
 * RBAC
 *
 * CLIENT <|--<<extends>>-- CONFIGURATOR
 * getA(Client)
 * getB(Configurator)
 */

interface Authentication {
    enum class Role {
        NONE,
        CLIENT,
        CONFIGURATOR,
    }

    fun role(): Role


}

class GetTheoriesUseCase(private val theoriesGateway: TheoriesGateway) {
    fun getTheories(): List<Theory> = theoriesGateway.getTheories()

    operator fun invoke(): List<Theory> = getTheories()
}
