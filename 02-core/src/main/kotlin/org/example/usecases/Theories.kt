package org.example.usecases

import org.example.entities.Theory
import org.example.gateway.TheoriesGateway

class TheoriesUseCases(theoriesGateway: TheoriesGateway) {
    val makeGetAllTheories: UseCase<Unit, List<String>> =
        UseCase.of("GetAllTheories") { theoriesGateway.getTheoriesIndex() }

    val makeGetTheoryByName: UseCase<String, Theory?> =
        UseCase.of("GetTheoryByName") { theoriesGateway.getTheoryByName(it) }

    val makeCreateTheory: UseCase<Theory, Unit> =
        UseCase.of("CreateTheory") { theoriesGateway.createTheory(it) }

}
