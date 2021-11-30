package org.example.gateway

import arrow.core.Either
import arrow.core.right
import org.example.UCException
import org.example.UCException.NotFoundException
import org.example.UCException.DuplicateIdentifierException
import org.example.entities.Prolog
import org.example.entities.Theory
import java.time.Instant

private val defaultTheory = Theory(
    name = "default",
    value = """
                parent(goku, gohan).
                parent(goku, goten).
            """.trimIndent()
)

data class InMemoryDatabase(
    val theories: Map<String, Theory> = mapOf("default" to defaultTheory)
)

class InMemoryTheoriesGateway(private var inMemoryDatabase: InMemoryDatabase) : TheoriesGateway {
    override suspend fun getTheoriesIndex(): Either<Nothing, List<String>> =
        inMemoryDatabase.theories.keys.toList().right()

    override suspend fun getTheoryByName(name: String): Either<NotFoundException, Theory> =
        Either.fromNullable(inMemoryDatabase.theories[name])
            .mapLeft { NotFoundException(name, "Couldn't find Theory named $name.") }

    override suspend fun createTheory(name: String, value: Prolog): Either<DuplicateIdentifierException, Theory> =
        getTheoryByName(name).swap()
            .map { updateTheoriesDatabase(Theory(name, value)) }
            .mapLeft { DuplicateIdentifierException(name, "Couldn't find Theory named $name.") }

    override suspend fun updateTheory(name: String, value: Prolog): Either<UCException, Theory> =
        getTheoryByName(name)
            .map { it.copy(value = value, version = it.version + 1, createdAt = Instant.now()) }
            .map(::updateTheoriesDatabase)

    override suspend fun deleteTheory(name: String): Either<NotFoundException, Theory> =
        getTheoryByName(name)
            .tap(::deleteTheoryFromDatabase)

    private fun updateTheoriesDatabase(theory: Theory): Theory =
        theory.apply {
            inMemoryDatabase = inMemoryDatabase
                .copy(theories = inMemoryDatabase.theories + (name to this))
        }

    private fun deleteTheoryFromDatabase(theory: Theory) {
        inMemoryDatabase = inMemoryDatabase
            .copy(theories = inMemoryDatabase.theories - theory.name)
    }
}