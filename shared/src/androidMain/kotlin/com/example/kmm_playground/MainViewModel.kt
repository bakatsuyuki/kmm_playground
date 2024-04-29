package com.example.kmm_playground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TodosRepository) : ViewModel() {
    private val _todos: MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())
    private val _checkedIds: MutableStateFlow<Set<Int>> = MutableStateFlow(emptySet())
    val unCheckedTodos =
        _todos.map { todos -> todos.filter { !it.completed } }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
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