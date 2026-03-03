package com.openclaw.javaguidesquiz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class QuizHomeScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun showsCoreElements() {
        composeRule.onNodeWithText("JavaGuide Quiz").assertIsDisplayed()
        composeRule.onNodeWithText("模式").assertIsDisplayed()
        composeRule.onNodeWithText("分类").assertIsDisplayed()
        composeRule.onNodeWithText("提交").assertIsDisplayed()
    }
}
