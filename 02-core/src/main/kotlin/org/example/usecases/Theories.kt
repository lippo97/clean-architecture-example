package org.example.usecases

import org.example.UCException
import org.example.UCException.NotFoundException
import org.example.entities.Prolog
import org.example.entities.Theory
import org.example.gateway.TheoriesGateway

class TheoriesUseCases(theoriesGateway: TheoriesGateway) {
    val makeGetAllTheoriesIndex: UseCase<Unit, Nothing, List<Pair<String, Int>>> =
        UseCase.of("GetAllTheories") { theoriesGateway.getTheoriesIndex() }

    val makeGetAllTheoriesIndexByName: UseCase<String, NotFoundException, List<Pair<String, Int>>> =
        UseCase.of("GetAllTheories") { theoriesGateway.getTheoriesIndex(it) }

    val makeGetTheoryByName: UseCase<String, NotFoundException, Theory> =
        UseCase.of("GetTheoryByName") { theoriesGateway.getTheoryByName(it) }

    val makeCreateTheory: UseCase<Pair<String, Prolog>, UCException, Theory> =
        UseCase.of("CreateTheory") { (name, value) ->
            theoriesGateway.createTheory(name, value)
        }

    val makeDeleteTheory: UseCase<String, NotFoundException, Theory> =
        UseCase.of("DeleteTheory", theoriesGateway::deleteTheory)

    val makeUpdateTheory: UseCase<Pair<String, Prolog>, UCException, Theory> =
        UseCase.of("UpdateTheory") { (name, value) ->
            theoriesGateway.updateTheory(name, value)
        }

    val makeGetTheoryByNameAndVersion: UseCase<Pair<String, Int>, NotFoundException, Theory> =
        UseCase.of("GetTheoryByNameAndVersion") { (name, version) ->
            theoriesGateway.getTheoryByNameAndVersion(name, version)
        }
}
