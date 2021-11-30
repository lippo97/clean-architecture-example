package org.example.gateway

import arrow.core.Either
import org.example.UCException
import org.example.UCException.NotFoundException
import org.example.entities.Prolog
import org.example.entities.Theory

interface TheoriesGateway {

    suspend fun getTheoriesIndex(): Either<Nothing, List<Pair<String, Int>>>

    suspend fun getTheoriesIndex(name: String): Either<NotFoundException, List<Pair<String, Int>>>

    suspend fun getTheoryByName(name: String): Either<NotFoundException, Theory>

    suspend fun createTheory(name: String, value: Prolog): Either<UCException, Theory>

    suspend fun updateTheory(name: String, value: Prolog): Either<UCException, Theory>

    suspend fun deleteTheory(name: String): Either<NotFoundException, Theory>

    suspend fun getTheoryByNameAndVersion(name: String, version: Int): Either<NotFoundException, Theory>
}
