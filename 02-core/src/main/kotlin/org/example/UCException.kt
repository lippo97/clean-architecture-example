package org.example

sealed interface UCException {
    data class NotFoundException(val id: String, val msg: String) : UCException
    data class DuplicateIdentifierException(val id: String, val msg: String) : UCException
}