package org.example.usecases

import arrow.core.Either

interface UseCase<in I, out E, out O> {
    suspend fun execute(input: I): Either<E, O>

    fun tag(): String

    companion object {
        fun <I, E, O> of(
            tag: String,
            execute: suspend (I) -> Either<E, O>,
        ): UseCase<I, E, O> =
            object : UseCase<I, E, O> {
                override suspend fun execute(input: I): Either<E, O> = execute(input)
                override fun tag(): String = tag
            }
    }
}

suspend fun <E, O> UseCase<Unit, E, O>.execute(): Either<E, O> =
    execute(Unit)

suspend fun <A, B, E, O> UseCase<Pair<A, B>, E, O>.execute(
    a: A,
    b: B
): Either<E, O> = execute(Pair(a, b))


suspend fun <A, B, C, E, O> UseCase<Triple<A, B, C>, E, O>.execute(
    a: A,
    b: B,
    c: C,
): Either<E, O> = execute(Triple(a, b, c))