package org.example.utils.either

import arrow.core.Either

/**
 * Transformer for Either<*, List<*>>.
 */
fun <E, A, B> Either<E, List<A>>.mapT(fab: (A) -> B): Either<E, List<B>> =
    this.map { it.map(fab) }
