package org.example.usecases

import arrow.core.right
import org.example.UCException
import org.example.UCException.DuplicateIdentifierException
import org.example.UCException.NotFoundException
import org.example.entities.Theory
import org.example.gateway.TheoriesGateway

class TheoriesUseCases(theoriesGateway: TheoriesGateway) {
    val makeGetAllTheories: UseCase<Unit, Nothing, List<String>> =
        UseCase.of("GetAllTheories") { theoriesGateway.getTheoriesIndex() }

    val makeGetTheoryByName: UseCase<String, NotFoundException, Theory> =
        UseCase.of("GetTheoryByName") { theoriesGateway.getTheoryByName(it) }

    val makeCreateTheory: UseCase<Pair<String, String>, UCException, Theory> =
        UseCase.of("CreateTheory") { (name, value) ->
            theoriesGateway.createTheory(name, value)
        }

    val makeDeleteTheory: UseCase<String, NotFoundException, Theory> =
        UseCase.of("DeleteTheory", theoriesGateway::deleteTheory)

    val makeUpdateTheory: UseCase<Pair<String, String>, UCException, Theory> =
        UseCase.of("UpdateTheory") { (name, value) ->
            theoriesGateway.updateTheory(name, value)
        }
}
