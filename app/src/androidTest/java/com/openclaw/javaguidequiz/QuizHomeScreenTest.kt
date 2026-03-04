package com.openclaw.javaguidesquiz

import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
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

        composeRule.onNodeWithTag("mode_sequential").assertIsDisplayed()
        composeRule.onNodeWithTag("mode_random").assertIsDisplayed()
        composeRule.onNodeWithTag("submit_button").assertIsDisplayed()
        composeRule.onNodeWithTag("category_label").assertIsDisplayed()
    }

    @Test
    fun submitFlowShowsResultAndCanGoNext() {
        composeRule.onNodeWithText("开始刷题").performClick()

        composeRule.onNodeWithTag("question_progress").assertExists()
        composeRule.onNodeWithTag("submit_button").performClick()

        composeRule.onNodeWithTag("result_card").assertExists()
        composeRule.onNodeWithTag("next_button").assertExists()

        composeRule.onNodeWithTag("next_button").performClick()
        composeRule.onNodeWithTag("question_progress").assertExists()
    }

    @Test
    fun canSwitchModeAndFilterCategory() {
        composeRule.onNodeWithText("开始刷题").performClick()

        composeRule.onNodeWithTag("mode_random").performClick()
        composeRule.onNodeWithTag("mode_sequential").performClick()

        composeRule.onNodeWithTag("category_label").assertIsDisplayed()
        composeRule.onNodeWithText("全部").performClick()

        composeRule.onNodeWithTag("question_progress").assertExists()
    }

    @Test
    fun canToggleFavoriteStateFromQuestionCard() {
        composeRule.onNodeWithText("开始刷题").performClick()

        composeRule.onNodeWithTag("favorite_button").assertExists()
        composeRule.onNodeWithTag("favorite_button").performClick()
        composeRule.onNodeWithText("取消收藏").assertExists()
    }

    @Test
    fun submitShouldBeDisabledAfterResultShown() {
        composeRule.onNodeWithText("开始刷题").performClick()

        composeRule.onNodeWithTag("submit_button").performClick()
        composeRule.onNodeWithTag("result_card").assertExists()
        composeRule.onNodeWithTag("submit_button").assertIsNotEnabled()
    }
}
