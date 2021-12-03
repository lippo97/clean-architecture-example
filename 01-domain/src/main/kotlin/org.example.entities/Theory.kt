package org.example.entities

import java.time.Instant

/**
 * A text consisting of a sequence of clauses and/or directives.
 *
 * @property name The unique name of the theory.
 * @property value The prolog source code.
 * @property version A non-negative progressive integer representing the version of the resource.
 * @property createdAt A temporal indication of the creation instant for the resource.
 */
data class Theory(
    val name: String,
    val value: Prolog,
    val version: Version = 0,
    val createdAt: Instant = Instant.now(),
)
