package org.example.delivery.httpclient

import arrow.core.getOrElse
import arrow.core.toOption
import io.vertx.core.Future
import io.vertx.core.http.HttpClient
import io.vertx.core.http.HttpClientResponse
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.Json


private fun HttpClient.requestDSL(
    method: HttpMethod,
    requestURI: String,
    host: String = "localhost",
    port: Int = 8080,
    body: (Json.() -> JsonObject)? = null,
): Future<HttpClientResponse> =
    request(method, port, host, requestURI)
        .flatMap { httpClientRequest ->
            body.toOption()
                .map { httpClientRequest.send(it(Json).toBuffer()) }
                .getOrElse { httpClientRequest.send() }
        }

fun HttpClient.get(
    requestURI: String,
    host: String = "localhost",
    port: Int = 8080,
    body: (Json.() -> JsonObject)? = null
): Future<HttpClientResponse> =
    requestDSL(HttpMethod.GET, requestURI, host, port, body)


fun HttpClient.post(
    requestURI: String,
    host: String = "localhost",
    port: Int = 8080,
    body: (Json.() -> JsonObject)? = null
): Future<HttpClientResponse> =
    requestDSL(HttpMethod.POST, requestURI, host, port, body)

fun HttpClient.patch(
    requestURI: String,
    host: String = "localhost",
    port: Int = 8080,
    body: (Json.() -> JsonObject)? = null
): Future<HttpClientResponse> =
    requestDSL(HttpMethod.PATCH, requestURI, host, port, body)

fun HttpClient.delete(
    requestURI: String,
    host: String = "localhost",
    port: Int = 8080,
    body: (Json.() -> JsonObject)? = null
): Future<HttpClientResponse> =
    requestDSL(HttpMethod.DELETE, requestURI, host, port, body)