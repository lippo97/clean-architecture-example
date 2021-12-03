package org.example.gateway

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.nonEmptyListOf
import arrow.core.right
import org.example.UCException
import org.example.UCException.DuplicateIdentifierException
import org.example.UCException.NotFoundException
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
    val theories: Map<String, NonEmptyList<Theory>> = mapOf("default" to nonEmptyListOf(defaultTheory))
)

private fun List<Pair<String, NonEmptyList<Theory>>>.combinations(): List<Pair<String, Int>> =
    this.toList()
        .flatMap { (key, theories) ->
            theories.map { Pair(key, it.version) }
        }

class InMemoryTheoriesGateway(private var inMemoryDatabase: InMemoryDatabase) : TheoriesGateway {
    override suspend fun getTheoriesIndex(): Either<Nothing, List<Pair<String, Int>>> =
        inMemoryDatabase.theories
            .toList()
            .combinations()
            .right()

    override suspend fun getTheoriesIndex(name: String): Either<NotFoundException, List<Pair<String, Int>>> =
        Either.fromNullable(
            inMemoryDatabase.theories
                .toList()
                .find { it.first == name }
        )
            .mapLeft { NotFoundException(name, "Couldn't find Theory named $name.") }
            .map {
                nonEmptyListOf(it).combinations()
            }

    override suspend fun getTheoryByName(name: String): Either<NotFoundException, Theory> =
        getTheoriesFromDatabase(name).map { it.last() }

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

    override suspend fun getTheoryByNameAndVersion(name: String, version: Int): Either<NotFoundException, Theory> =
        getTheoriesFromDatabase(name)
            .flatMap { theories ->
                Either.fromNullable(theories.find { it.version == version })
            }
            .mapLeft { NotFoundException(name, "Couldn't find Theory named $name at version $version.") }

    private fun getTheoriesFromDatabase(name: String): Either<NotFoundException, NonEmptyList<Theory>> =
        Either.fromNullable(inMemoryDatabase.theories[name])
            .mapLeft { NotFoundException(name, "Couldn't find Theory named $name.") }

    private fun updateTheoriesDatabase(theory: Theory): Theory =
        theory.apply {
            inMemoryDatabase = inMemoryDatabase.run {
                copy(
                    theories = theories +
                        (
                            name to
                                (theories[name]?.plus(theory) ?: nonEmptyListOf(theory))
                            )
                )
            }
        }

    private fun deleteTheoryFromDatabase(theory: Theory) {
        inMemoryDatabase = inMemoryDatabase
            .copy(theories = inMemoryDatabase.theories - theory.name)
    }
}
