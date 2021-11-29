package org.example

sealed interface UCException {
    data class NotFoundException(val msg: String) : UCException
    data class DuplicateIdentifierException(val id: Int, val msg: String) : UCException
}