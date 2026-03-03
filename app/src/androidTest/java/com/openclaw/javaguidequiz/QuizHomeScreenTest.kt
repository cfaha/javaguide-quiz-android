package com.openclaw.javaguidesquiz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class QuizHomeScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun showsHomeEntryAndCanStartPractice() {
        composeRule.onNodeWithText("JavaGuide 刷题").assertIsDisplayed()
        composeRule.onNodeWithText("今日刷题").assertIsDisplayed()
        composeRule.onNodeWithText("开始刷题").assertIsDisplayed()
        composeRule.onNodeWithText("错题重练").assertIsDisplayed()

        composeRule.onNodeWithText("开始刷题").performClick()

        composeRule.onNodeWithText("顺序").assertIsDisplayed()
        composeRule.onNodeWithText("随机").assertIsDisplayed()
        composeRule.onNodeWithText("提交").assertIsDisplayed()
        composeRule.onNodeWithText("分类").assertIsDisplayed()
    }
}
