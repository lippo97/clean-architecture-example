package org.example.authentication

import org.example.Authentication
import org.example.Authentication.Role


interface RoleHierarchy<Role> {
    fun addRole(role: Role): Unit

    fun addRole(role: Role, parent: Role): Unit

    fun isChildOf(child: Role, parent: Role): Boolean
}

fun <Role>RoleHierarchy<Role>.checkPermissions(child: Role, parent: Role, ifAllowed: () -> Unit): Unit {
    if (isChildOf(child, parent)) {

        ifAllowed()
    }

    throw UnsupportedOperationException("ciao")
}

typealias Operation = () -> Unit
typealias ProtectedOperation = Pair<Role, Operation>

val basicOperation: ProtectedOperation = Pair(Client()) {}

val secretOperation: ProtectedOperation = Pair(Configurator()) {}

val clientRole = Client()

fun aaaaa(
    operation: ProtectedOperation,
    roleHierarchy: RoleHierarchy<Authentication.Role>,
    authentication: Authentication,
) {
    roleHierarchy.addRole(Role.CLIENT)
    roleHierarchy.addRole(Role.CONFIGURATOR, Role.CLIENT)

    //

    val role = authentication.role()
    val operationRole = operation.first

    roleHierarchy.checkPermissions(role, operationRole) {

    }


}