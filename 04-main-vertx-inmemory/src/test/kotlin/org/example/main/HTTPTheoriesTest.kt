package org.example.main

import arrow.core.getOrElse
import arrow.core.toOption
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.http.*
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.*
import io.vertx.kotlin.coroutines.await
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.example.delivery.Controller
import org.example.delivery.httpclient.*
import org.example.gateway.InMemoryDatabase
import org.example.gateway.InMemoryTheoriesGateway


class HTTPTheoriesTest : FunSpec({

    val vertx = Vertx.vertx()
    val client = vertx.createHttpClient()

    val exampleTheory = """
        parent(goku, gohan).
        parent(goku, goten).
    """.trimIndent()

    val anotherExampleTheory = """
        $exampleTheory
        parent(bardok, goku).
    """.trimIndent()

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
        client.get("/theories")
            .map { it.statusCode() }
            .map { it shouldBeExactly 200 }
            .await()
    }

    test("The service should be initialized with a theory") {
        client.get("/theories")
            .flatMap { it.body() }
            .map {
                it.toJson() shouldBe json { array("/theories/default/versions/0") }
            }
            .await()
    }

    context("When a new theory is submitted") {
        test("a resource must be created successfully") {
            client.post("/theories") {
                obj(
                    "name" to "myTheory",
                    "value" to exampleTheory
                )
            }
                .map { it.statusCode() }
                .map { it shouldBeExactly 201 }
        }
        test("the theories index must be updated") {
            client.get("/theories")
                .flatMap { it.body() }
                .map { it.toJson() shouldBe json {
                    array(
                        "/theories/default/versions/0",
                        "/theories/myTheory/versions/0"
                    )
                } }
                .await()
        }
        test("it can be retrieved at its URI") {
            client.get("/theories/myTheory")
                .flatMap { it.body() }
                .map {
                    it.toJsonObject().get<String>("name") shouldBe "myTheory"
                    it.toJsonObject().get<String>("value") shouldBe exampleTheory
                }
                .await()
        }
    }

    context("When an existing theory is updated") {
        test("it should return the updated record") {
            client.patch("/theories/myTheory") {
                obj(
                    "value" to anotherExampleTheory
                )
            }
                .flatMap { it.body() }
                .map {
                    it.toJsonObject().get<String>("name") shouldBe "myTheory"
                    it.toJsonObject().get<String>("value") shouldBe anotherExampleTheory
                    it.toJsonObject().get<Int>("version") shouldBeExactly 1
                }
                .await()
        }
        test("it should keep the old one") {
            client.get("/theories/myTheory/versions/0")
                .flatMap {
                    it.statusCode() shouldBeExactly 200
                    it.body()
                }
                .map {
                    it.toJsonObject().get<String>("name") shouldBe "myTheory"
                    it.toJsonObject().get<String>("value") shouldBe exampleTheory
                    it.toJsonObject().get<Int>("version") shouldBeExactly 0
                }
                .await()
        }
        test("it should update its index") {
            client.get("/theories/myTheory/versions")
                .flatMap {
                    it.statusCode() shouldBeExactly 200
                    it.body()
                }
                .map {
                    it.toJsonArray() shouldBe json { array(
                        "/theories/myTheory/versions/0",
                        "/theories/myTheory/versions/1",
                    ) }
                }
                .await()
        }
        test("latest should be the default one") {
            client.get("/theories/myTheory")
                .flatMap { it.body() }
                .map {
                    it.toJsonObject().apply {
                        getString("name") shouldBe "myTheory"
                        getString("value") shouldBe anotherExampleTheory
                        getInteger("version") shouldBeExactly 1
                    }
                }
                .await()
        }
    }

    context("When a theory is deleted") {
        test("request should complete successfully") {
            client.delete("/theories/myTheory/")
                .map {
                    it.statusCode() shouldBe 200
                }
                .await()
        }
        test("all its version should be removed") {
            client.get("/theories")
                .flatMap { it.body() }
                .map {
                    it.toJson() shouldBe json { array(
                        "/theories/default/versions/0"
                    ) }
                }
                .await()
        }
    }

})