package com.example.androidlab02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// 修改：isFinished 使用 MutableState，让Compose监听状态变化
data class Task(
    val id: Int,
    val title: String,
    val isFinished: MutableState<Boolean>
)

class TodoTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskPage()
                }
            }
        }
    }
}

@Composable
fun TaskPage() {
    val taskList: SnapshotStateList<Task> = remember {
        mutableStateListOf(
            Task(id = 1, title = "学习Column和Row", isFinished = mutableStateOf(true)),
            Task(id = 2, title = "学习状态管理", isFinished = mutableStateOf(false)),
            Task(id = 3, title = "完成Compose实验", isFinished = mutableStateOf(false))
        )
    }

    var inputText by remember { mutableStateOf("") }

    // 统计已完成数量
    val finishedCount = taskList.count { it.isFinished.value }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "课程学习任务",
            style = MaterialTheme.typography.headlineSmall
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("请输入学习任务") },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        val newId = taskList.maxOf { it.id } + 1
                        taskList.add(
                            Task(
                                id = newId,
                                title = inputText.trim(),
                                isFinished = mutableStateOf(false)
                            )
                        )
                        inputText = ""
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("添加")
            }
        }

        Text(
            text = "已完成: $finishedCount / ${taskList.size}",
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(taskList, key = { it.id }) { task ->
                TaskItem(
                    task = task,
                    onDelete = {
                        taskList.remove(task)
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isFinished.value,
            onCheckedChange = {
                task.isFinished.value = it
            }
        )

        Text(
            text = task.title,
            modifier = Modifier.weight(1f),
            textDecoration = if (task.isFinished.value) TextDecoration.LineThrough else TextDecoration.None
        )

        Button(onClick = onDelete) {
            Text("删除")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskPage() {
    MaterialTheme {
        TaskPage()
    }
}
