package org.example.main

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.vertx.core.Vertx
import io.vertx.core.http.HttpClientRequest
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.RequestOptions
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.coroutines.await
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.example.delivery.Controller
import org.example.gateway.InMemoryDatabase
import org.example.gateway.InMemoryTheoriesGateway

class HTTPInMemoryTests : FunSpec({

    val vertx = Vertx.vertx()
    val client = vertx.createHttpClient()

    beforeAny {
        runTest {
            val server = vertx.createHttpServer()
            val controller = Controller.of(
                vertx = vertx,
                theoriesGateway = InMemoryTheoriesGateway(InMemoryDatabase())
            )
            server
                .requestHandler(controller.routes())
                .listen(8080).await()
        }
    }

    test("The service should be running") {
        client.request(HttpMethod.GET, 8080, "localhost", "/theories")
            .flatMap { it.send() }
            .map { it.statusCode() }
            .map { it shouldBeExactly 200 }
            .await()
    }

    test("The service should be inizialized with a theory") {
        client.request(HttpMethod.GET,8080,"localhost","/theories")
            .flatMap { it.send() }
            .flatMap { it.body() }
            .map {
                it.toJson() shouldBe json { array("ciao") }
            }
            .await()

//        response.body().await().toJson() shouldBe json {
//            array {
//
//            }
//        }
    }

    test("This test should fail") {
        "ciao".length shouldBeExactly 100
    }

})