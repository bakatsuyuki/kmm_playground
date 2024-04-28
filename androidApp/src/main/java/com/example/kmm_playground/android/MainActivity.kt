package com.example.kmm_playground.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmm_playground.Todo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoList()
                }
            }
        }
    }
}

@Composable
fun TodoList(viewModel: MainViewModel = viewModel()) {
    val todos by viewModel.unCheckedTodos.collectAsState(initial = emptyList())
    val checkedIds by viewModel.checkedIds.collectAsState(initial = emptyList())
    return LazyColumn {
        items(todos.size) { index ->
            val todo = todos[index]
            TodoListTile(todo, !checkedIds.contains(todo.id))
        }
    }
}

@Composable
fun TodoListTile(todo: Todo, isVisible: Boolean, viewModel: MainViewModel = viewModel()) {
    return AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
        exit = fadeOut() + shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(
                500,
                200,
            ),
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            TodoCheckButton(todo.id, viewModel)
            Text(
                text = todo.title,
            )
        }
    }
}

@Composable
fun TodoCheckButton(id: Int, viewModel: MainViewModel = viewModel()) {
    val checkedIds by viewModel.checkedIds.collectAsState(initial = emptyList())
    return CheckButton(
        onClick = {
            viewModel.check(id)
        }, checked = checkedIds.contains(id)
    )
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        TodoList()
    }
}
