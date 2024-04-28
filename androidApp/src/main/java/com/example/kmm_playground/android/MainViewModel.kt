package com.example.kmm_playground.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm_playground.Todo
import com.example.kmm_playground.TodosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = TodosRepository()
    private val _todos: MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())
    private val todos = _todos.asStateFlow()
    val unCheckedTodos = _todos.asStateFlow().map { todos -> todos.filter { !it.completed } }

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            repository.listTodos().collect {
                _todos.value = it
            }
        }
    }
}