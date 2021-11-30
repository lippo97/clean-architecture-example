package org.example.rbac

enum class Role {
    NONE,
    CLIENT,
    CONFIGURATOR,
}

interface RoleBasedAccessControl {

    fun <T> setPermission(useCase: Class<T>, myRole: Role): Unit

    fun <T> checkPermission(useCase: Class<T>, myRole: Role, ifAllowed: () -> Unit): Unit
}

// Dentro al livello 02-core

abstract class UseCase {
    val TAG: String = this::class.java.simpleName
}

class GetTheoriesUseCase2(a: Int, b: String) : UseCase() {
    fun getTheories(): List<Int> = listOf(1, 2, 3, 4, 5)
}

typealias Tag = String

interface RoleBasedAccessControl2 {

    fun setPermission(useCase: Tag, myRole: Role): Unit

    fun checkPermission(useCase: Tag, myRole: Role): Boolean

    fun checkPermission(useCase: Tag, myRole: Role, ifAllowed: () -> Unit): Unit

    fun <T : UseCase> checkPermission(useCase: T, myRole: Role, ifAllowed: T.() -> Unit): Unit
}
