package com.example.kmm_playground.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm_playground.Todo
import com.example.kmm_playground.TodosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = TodosRepository()
    private val _todos: MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())
    private val _checkedIds: MutableStateFlow<Set<Int>> = MutableStateFlow(emptySet())
    val unCheckedTodos = _todos.asStateFlow().map { todos -> todos.filter { !it.completed } }
    val checkedIds = _checkedIds.asStateFlow()

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

    fun check(id: Int) {
        _checkedIds.update { current ->
            current + id
        }
    }
}