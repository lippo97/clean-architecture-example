package org.example.gateway

import arrow.core.Either
import arrow.core.right
import org.example.UCException
import org.example.UCException.NotFoundException
import org.example.entities.Prolog
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
    override fun getTheoriesIndex(): Either<Nothing, List<String>> =
        inMemoryDatabase.theories.keys.toList().right()

    override fun getTheoryByName(name: String): Either<NotFoundException, Theory> =
        Either.fromNullable(inMemoryDatabase.theories[name])
            .mapLeft { NotFoundException("Couldn't find theory named $name.") }

    override fun createTheory(name: String, value: Prolog): Either<UCException, Theory> {
        TODO("Not yet implemented")
    }

    override fun updateTheory(name: String, value: Prolog): Either<UCException, Theory> {
        TODO("Not yet implemented")
    }

    override fun deleteTheory(name: String): Either<UCException.NotFoundException, Theory> {
        TODO("Not yet implemented")
    }


}