package org.example.usecases

import arrow.core.Either

interface UseCase<in Input, out Exception ,out Output> {
    fun execute(input: Input): Either<Exception, Output>

    fun tag(): String

    companion object {
        fun <Input, Exception, Output>of(
            tag: String,
            execute: (Input) -> Either<Exception, Output>,
        ): UseCase<Input, Exception, Output> =
            object : UseCase<Input, Exception, Output> {
                override fun execute(input: Input): Either<Exception, Output> = execute(input)
                override fun tag(): String = tag
            }
    }
}

// Second version
//fun interface GetTheoryByNameUseCase : UseCase<String, Theory?> {
//    override fun execute(input: String): Theory?
//
//    override fun tag(): String = "GetTheoryByName"
//
//    companion object {
//        fun of(theoriesGateway: TheoriesGateway): GetTheoryByNameUseCase =
//            GetTheoryByNameUseCase { theoriesGateway.getTheoryByName(it) }
//    }
//}