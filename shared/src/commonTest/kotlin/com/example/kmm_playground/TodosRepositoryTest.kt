package com.example.kmm_playground

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TodosRepositoryTest {
    private lateinit var mockEngine: MockEngine
    private lateinit var client: HttpClient

    @BeforeTest
    fun setUp() {
        mockEngine = MockEngine { _ ->
            respond(
                content = """[{"userId": 1, "id": 1, "title": "Test Todo", "completed": false}]""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Test
    fun testListTodos() = runTest {
        val repository = TodosRepository(client)
        val todos = repository.listTodos().toList().flatten()
        assertEquals(
            listOf(Todo(1, 1, "Test Todo", false)),
            todos,
        )
    }
}
