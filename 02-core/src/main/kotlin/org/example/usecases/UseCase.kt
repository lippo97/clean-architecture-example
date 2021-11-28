package org.example.usecases

interface UseCase<in Input, out Output> {
    fun execute(input: Input): Output

    fun tag(): String

    companion object {
        fun <Input, Output>of(tag: String, execute: (Input) -> Output): UseCase<Input, Output> =
            object : UseCase<Input, Output> {
                override fun execute(input: Input): Output = execute(input)
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