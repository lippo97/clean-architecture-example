package org.example.usecases.data

/**
 * It represents the resolution of a [Goal] on a specific [Theory].
 *
 * @property goal The goal to resolve.
 * @property theory The theory to resolve the goal on.
 */
data class Query(
    val goal: Goal,
    val theory: Theory,
)