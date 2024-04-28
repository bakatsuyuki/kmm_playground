package com.example.kmm_playground

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class TodosRepository {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    fun listTodos(): Flow<List<Todo>> = flow {
        val response = client.get("$BASE_URL/users/1/todos")
        val jsonArray = response.body<List<Todo>>()
        emit(jsonArray)
    }
}

const val BASE_URL = "https://jsonplaceholder.typicode.com"
