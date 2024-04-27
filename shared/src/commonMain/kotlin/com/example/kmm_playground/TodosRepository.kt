package com.example.kmm_playground

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodosRepository {
    private val client = HttpClient(CIO)
    fun listTodos(): Flow<String> = flow {
        val response = client.get("https://jsonplaceholder.typicode.com/todos")
        emit(response.body<String>())
    }
}