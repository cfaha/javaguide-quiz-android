package com.openclaw.javaguidesquiz.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.openclaw.javaguidesquiz.domain.model.PracticeMode
import com.openclaw.javaguidesquiz.domain.model.QuestionType
import com.openclaw.javaguidesquiz.ui.viewmodel.PracticeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizHomeScreen(vm: PracticeViewModel = viewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    val current = state.current

    Scaffold(topBar = { TopAppBar(title = { Text("JavaGuide 刷题") }) }) { padding ->
        if (!state.started) {
            HomeEntry(
                modifier = Modifier.padding(padding),
                score = state.score,
                wrongCount = state.wrongBook.size,
                onStart = vm::startPractice,
                onWrong = {
                    vm.setCategory("全部")
                    vm.setMode(PracticeMode.RANDOM)
                    vm.startPractice()
                }
            )
            return@Scaffold
        }

        if (state.completed) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("本轮完成 🎉", style = MaterialTheme.typography.titleLarge)
                Text("得分：${state.score}/${state.total}")
                Text("错题数：${state.wrongBook.size}，收藏数：${state.favorites.size}")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = vm::restart) { Text("再来一轮") }
                    Button(onClick = vm::backHome) { Text("返回首页") }
                }
            }
            return@Scaffold
        }

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
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("第 ${state.index + 1}/${state.total} 题")
                        Text(current.category)
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = state.mode == PracticeMode.SEQUENTIAL, onClick = { vm.setMode(PracticeMode.SEQUENTIAL) }, label = { Text("顺序") })
                    FilterChip(selected = state.mode == PracticeMode.RANDOM, onClick = { vm.setMode(PracticeMode.RANDOM) }, label = { Text("随机") })
                }
            }

            item {
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
                    Button(onClick = vm::toggleFavorite) { Text(if (current.id in state.favorites) "取消收藏" else "收藏") }
                    if (state.showResult) {
                        Button(onClick = vm::next) { Text(if (state.index < state.total - 1) "下一题" else "完成") }
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
            }

            item {
                Text("当前得分：${state.score}")
            }

            item {
                Text("分类")
            }
            items(state.categories) { category ->
                FilterChip(selected = state.selectedCategory == category, onClick = { vm.setCategory(category) }, label = { Text(category) })
            }
        }
    }
}

@Composable
private fun HomeEntry(
    modifier: Modifier = Modifier,
    score: Int,
    wrongCount: Int,
    onStart: () -> Unit,
    onWrong: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("今日刷题", style = MaterialTheme.typography.titleMedium)
                Text("当前积分：$score")
                Text("累计错题：$wrongCount")
            }
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = onStart) { Text("开始刷题") }
        Button(modifier = Modifier.fillMaxWidth(), onClick = onWrong) { Text("错题重练") }
    }
}
