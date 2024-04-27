package com.example.kmm_playground.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm_playground.TodosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = TodosRepository()
    private val _todos = MutableStateFlow("")
    val todos = _todos.asStateFlow()

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