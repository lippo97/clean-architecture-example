package org.example.usecases

import org.example.UCException
import org.example.UCException.NotFoundException
import org.example.entities.Prolog
import org.example.entities.Theory
import org.example.gateway.TheoriesGateway

fun TheoriesGateway.getAllTheoriesIndexUseCase(): UseCase<Nothing, List<Pair<String, Int>>> =
    UseCase.of("GetAllTheories") { getTheoriesIndex() }

fun TheoriesGateway.getAllTheoriesIndexUseCase(name: String): UseCase<NotFoundException, List<Pair<String, Int>>> =
    UseCase.of("GetAllTheories") { getTheoriesIndex(name) }

fun TheoriesGateway.getTheoryByNameUseCase(name: String): UseCase<NotFoundException, Theory> =
    UseCase.of("GetTheoryByName") { getTheoryByName(name) }

fun TheoriesGateway.createTheoryUseCase(name: String, value: Prolog): UseCase<UCException, Theory> =
    UseCase.of("CreateTheory") { createTheory(name, value) }

fun TheoriesGateway.deleteTheoryUseCase(name: String): UseCase<NotFoundException, Theory> =
    UseCase.of("DeleteTheory") { deleteTheory(name) }

fun TheoriesGateway.updateTheoryUseCase(name: String, value: Prolog): UseCase<UCException, Theory> =
    UseCase.of("UpdateTheory") { updateTheory(name, value) }

fun TheoriesGateway.getTheoryByNameAndVersionUseCase(name: String, version: Int): UseCase<NotFoundException, Theory> =
    UseCase.of("GetTheoryByNameAndVersion") { getTheoryByNameAndVersion(name, version) }

