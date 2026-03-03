package com.openclaw.javaguidesquiz.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val sections = listOf("Java基础", "并发", "JVM", "MySQL", "Redis", "网络", "系统设计")

@Composable
fun QuizHomeScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("JavaGuide Quiz") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("MVP 已就绪", style = MaterialTheme.typography.titleMedium)
                        Text("支持单选 / 多选 / 填空题型，后续可接入题库导入与错题本。")
                    }
                }
            }
            items(sections) { sec ->
                AssistChip(onClick = { }, label = { Text(sec) })
            }
        }
    }
}
