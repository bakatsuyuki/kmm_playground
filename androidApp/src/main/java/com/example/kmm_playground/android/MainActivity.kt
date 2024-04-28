package com.example.kmm_playground.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
    val todos by viewModel.todos.collectAsState()
    return LazyColumn {
        items(todos.size) { index ->
            TodoListTile(todos[index])
        }
    }
}

@Composable
fun TodoListTile(todo: Todo) {
    return Row(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
    ) {
        CheckButton(
            onCheckedChange = { Log.d("MainActivity", "Checked: $it") },
            checked = todo.completed,
        )
        Text(
            text = todo.title,
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        TodoList()
    }
}
