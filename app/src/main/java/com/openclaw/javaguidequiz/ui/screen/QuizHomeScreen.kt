package com.openclaw.javaguidesquiz.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.openclaw.javaguidesquiz.domain.model.QuestionType
import com.openclaw.javaguidesquiz.ui.viewmodel.PracticeViewModel

@Composable
fun QuizHomeScreen(vm: PracticeViewModel = viewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    val current = state.current

    Scaffold(topBar = { TopAppBar(title = { Text("JavaGuide Quiz") }) }) { padding ->
        if (current == null) {
            Text("暂无题目", modifier = Modifier.padding(padding))
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("第 ${state.index + 1}/${state.total} 题 · ${current.category}")
                Text(current.stem, style = MaterialTheme.typography.titleMedium)
            }

            if (current.type != QuestionType.BLANK) {
                itemsIndexed(current.options) { idx, item ->
                    FilterChip(
                        selected = idx in state.selectedOptions,
                        onClick = { vm.onSelectOption(idx) },
                        label = { Text(item) }
                    )
                }
            } else {
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.blankInput,
                        onValueChange = vm::onBlankInput,
                        label = { Text("请输入答案") }
                    )
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = vm::submit, enabled = !state.showResult) { Text("提交") }
                    Button(onClick = vm::toggleFavorite) {
                        Text(if (current.id in state.favorites) "取消收藏" else "收藏")
                    }
                }
            }

            if (state.showResult) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(if (state.isCorrect == true) "回答正确 ✅" else "回答错误 ❌")
                            Text("答案：${current.answers.joinToString(" / ")}")
                            Text("解析：${current.explanation}")
                        }
                    }
                }
                item {
                    Button(onClick = vm::next, enabled = state.index < state.total - 1) {
                        Text(if (state.index < state.total - 1) "下一题" else "已是最后一题")
                    }
                }
            }

            item {
                Text("当前得分：${state.score}")
                Text("错题数：${state.wrongBook.size}，收藏数：${state.favorites.size}")
            }
        }
    }
}
