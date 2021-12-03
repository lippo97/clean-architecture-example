package org.example.usecases

import arrow.core.Either

interface UseCase<E, A> {
    fun tag(): String

    suspend fun execute(): Either<E, A>

    companion object {
        fun <E, A> of(tag: String, execute: suspend () -> Either<E, A>): UseCase<E, A> =
            object : UseCase<E, A> {
                override fun tag(): String = tag

                override suspend fun execute(): Either<E, A> = execute()
            }
    }
}
