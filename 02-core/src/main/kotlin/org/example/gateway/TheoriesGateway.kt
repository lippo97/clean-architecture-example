package org.example.gateway

import arrow.core.Either
import org.example.UCException
import org.example.UCException.NotFoundException
import org.example.UCException.DuplicateIdentifierException
import org.example.entities.Prolog
import org.example.entities.Theory

interface TheoriesGateway {

    fun getTheoriesIndex(): Either<Nothing, List<String>>

    fun getTheoryByName(name: String): Either<NotFoundException, Theory>

    fun createTheory(name: String, value: Prolog): Either<UCException, Theory>

    fun updateTheory(name: String, value: Prolog): Either<UCException, Theory>

    fun deleteTheory(name: String): Either<NotFoundException, Theory>
}
