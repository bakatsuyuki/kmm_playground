package com.example.kmm_playground

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.get

class TodosRepository {
    private val client = HttpClient(CIO)
    suspend fun listTodos(): String {
        val response = client.get("https://jsonplaceholder.typicode.com/todos")
        return response.body<String>()
    }
}