package org.example.gateway

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Fact
import it.unibo.tuprolog.core.Struct
import org.example.Theory

class InMemoryDatabase {
    val theories: List<Theory> = listOf(
            Theory(listOf(Fact.of(
                    Atom.of("hello")),
                    Fact.of(Atom.of("hi"))
            )),
            Theory(listOf(Fact.of(
                    Atom.of("ciao")),
                    Fact.of(Atom.of("salve"))
            )),
            Theory(listOf(Fact.of(
                    Atom.of("bonjour")),
                    Fact.of(Atom.of("salut"))
            )),
    )
}

class InMemoryTheoriesGateway(private val inMemoryDatabase: InMemoryDatabase) : TheoriesGateway {
    override fun getTheories(): List<Theory> = inMemoryDatabase.theories
}