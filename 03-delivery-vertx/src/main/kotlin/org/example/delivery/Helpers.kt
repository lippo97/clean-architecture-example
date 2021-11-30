package org.example.delivery

import arrow.core.Either
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.example.UCException

fun String.parseInt(): Either<UCException.ValidationError, Int> =
    Either.catch({
        when (it) {
            is NumberFormatException -> UCException.ValidationError("Expected int number, found $this")
            else -> throw it
        }
    }, this::toInt)

fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit) {
    handler { ctx ->
        GlobalScope.launch(ctx.vertx().dispatcher()) {
            runCatching { fn(ctx) }
                .onFailure(ctx::fail)
        }
    }
}

fun handleException(ctx: RoutingContext): (ucException: UCException) -> Unit = {
    when (it) {
        is UCException.DuplicateIdentifierException -> ctx.response()
            .setStatusCode(HTTPStatusCode.CONFLICT)
            .setStatusMessage(it.msg)
            .end()
        is UCException.NotFoundException -> ctx.response()
            .setStatusCode(HTTPStatusCode.NOT_FOUND)
            .setStatusMessage(it.msg)
            .end()
    }
}

fun <A> Either<UCException, A>.handleEither(ctx: RoutingContext) {
    fold(handleException(ctx), ctx::json)
}

fun <A> Route.coroutineHandlerEither(routeHandler: suspend (RoutingContext) -> Either<UCException, A>) {
    handler {
        GlobalScope.launch(it.vertx().dispatcher()) {
            runCatching {
                routeHandler(it)
                    .handleEither(it)
            }.onFailure(it::fail)
        }
    }
}