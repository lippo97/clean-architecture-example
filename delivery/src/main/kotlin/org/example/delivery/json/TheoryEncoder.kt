package org.example.delivery.json

import io.vertx.core.json.Json
import org.example.Theory

object TheoryEncoder {

    data class SimpleFact(val body: String)

    fun encode(theories: List<Theory>): String = Json.encodePrettily {
        theories.map { theory ->
            println(theory.facts.map { it.head.functor })
            theory.facts.map { SimpleFact(it.head.functor) }
        }
    }
}