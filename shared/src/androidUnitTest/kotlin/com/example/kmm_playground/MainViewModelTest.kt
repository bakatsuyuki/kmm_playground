package com.example.kmm_playground

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals


class MainViewModelTest {
    private lateinit var listTodos: (() -> List<Todo>)
    private lateinit var repository: TodosRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        listTodos = { listOf() }
        coEvery { repository.listTodos() } returns flow { emit(listTodos.invoke()) }
    }

    @After
    @ExperimentalCoroutinesApi
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initTest() = runTest {
        val uncompletedTodo = Todo(1, 3, "Buy milk", false)
        val completedTodo = Todo(2, 4, "Take dog for a walk", true)
        val todosList = listOf(uncompletedTodo, completedTodo)
        listTodos = { todosList }

        val viewModel = MainViewModel(repository)
        assertEquals(listOf(uncompletedTodo), viewModel.unCheckedTodos.first())
    }

    @Test
    fun checkTest(): Unit = runTest {
        val viewModel = MainViewModel(repository)
        // check 1
        viewModel.check(1)
        assertEquals(setOf(1), viewModel.checkedIds.value)

        // check 2
        viewModel.check(2)
        assertEquals(setOf(1, 2), viewModel.checkedIds.value)

        // check 2 but not change because there's already 2
        viewModel.check(2)
        assertEquals(setOf(1, 2), viewModel.checkedIds.value)
    }
}