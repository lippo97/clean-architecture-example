package org.example.gateway

import org.example.entities.Theory

class InMemoryDatabase {
    private val defaultTheory = Theory(
        name = "default",
        value = """
                parent(goku, gohan).
                parent(goku, goten).
            """.trimIndent()
    )

    val theories: MutableMap<String, Theory> = mutableMapOf(
        Pair("default", defaultTheory)
    )
}

class InMemoryTheoriesGateway(private val inMemoryDatabase: InMemoryDatabase) : TheoriesGateway {
    override fun getTheoriesIndex(): List<String> =
        inMemoryDatabase.theories.keys.toList()

    override fun getTheoriesByName(names: List<String>): List<Theory> =
        inMemoryDatabase.theories.entries
            .filter { names.contains(it.key) }
            .map { it.value }

    override fun createTheory(theory: Theory): Unit =
        inMemoryDatabase.theories.put(theory.name, theory).let { }
}