package org.example.rbac

import org.example.Authentication.Role
import org.example.GetTheoriesUseCase

interface RoleBasedAccessControl {

    fun <T>setPermission(useCase: Class<T>, myRole: Role): Unit

    fun <T>checkPermission(useCase: Class<T>, myRole: Role, ifAllowed: () -> Unit): Unit
}

fun livello4main(
    roleBasedAccessControl: RoleBasedAccessControl,
) {
    roleBasedAccessControl.apply {
        setPermission(GetTheoriesUseCase::class.java, Role.CLIENT)
        setPermission(GetTheoriesUseCase::class.java, Role.CLIENT)
        setPermission(GetTheoriesUseCase::class.java, Role.CLIENT)
        setPermission(GetTheoriesUseCase::class.java, Role.CLIENT)
    }
}

fun livello3vertx(
    roleBasedAccessControl: RoleBasedAccessControl,
    userRole: Role,
    ) {

        roleBasedAccessControl.checkPermission(GetTheoriesUseCase::class.java, userRole) {

        }

}

// Dentro al livello 02-core

annotation class UseCaseTag(val name: String)

@UseCaseTag("GetTheoriesUseCase")
class GetTheoriesUseCase() {

}

fun asdfasdf(roleBasedAccessControl: RoleBasedAccessControl,) {
    val getTheoriesUseCase = GetTheoriesUseCase()

    roleBasedAccessControl.checkPermission(getTheoriesUseCase.javaClass, Role.CLIENT) {

    }
}



typealias Tag = String
interface RoleBasedAccessControl2 {
    fun setPermission(useCase: Tag, myRole: Role): Unit

    fun checkPermission(useCase: Tag, myRole: Role, ifAllowed: () -> Unit): Unit
}

fun livello4main(
    roleBasedAccessControl: RoleBasedAccessControl2,
) {
    roleBasedAccessControl.apply {
        setPermission("GetTheoriesUseCase", Role.CLIENT)
    }
}

fun livello3vertx(
    roleBasedAccessControl: RoleBasedAccessControl2,
    userRole: Role,
) {

    roleBasedAccessControl.checkPermission("GetTheoriesUseCase", userRole) {
        get("/theories").handler { ctx ->
            val role = ctx.getRole()
            GetTheoriesUseCase()
        }
    }

}
