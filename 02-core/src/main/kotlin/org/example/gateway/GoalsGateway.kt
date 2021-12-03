package org.example.gateway

import arrow.core.Either
import org.example.UCException
import org.example.entities.Goal

interface GoalsGateway {
    suspend fun getGoalByName(name: String): Either<UCException.NotFoundException, Goal>
}
