package org.example.gateway

import org.example.entities.Theory

interface TheoriesGateway {

    fun getTheoriesIndex(): List<String>

    fun getTheoryByName(name: String): Theory? =
        getTheoriesByName(listOf(name)).firstOrNull()

    fun getTheoriesByName(names: List<String>): List<Theory>

    fun createTheory(theory: Theory): Unit
}
