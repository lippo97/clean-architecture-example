package org.example.gateway

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Clause
import it.unibo.tuprolog.core.Fact
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.theory.Theory

class InMemoryDatabase {
    val theories: List<Theory> = listOf(
        Theory.of(
            Clause.of(Atom.of("goku") , Atom.of("mario")),
            Clause.of(Atom.of("mario"), Atom.of("luigi")),
        )
    )
}

class InMemoryTheoriesGateway(private val inMemoryDatabase: InMemoryDatabase) : TheoriesGateway {
    override fun getTheories(): List<Theory> = inMemoryDatabase.theories
}