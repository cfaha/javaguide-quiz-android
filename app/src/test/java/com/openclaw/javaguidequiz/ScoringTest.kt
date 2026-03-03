package com.openclaw.javaguidesquiz

import com.openclaw.javaguidesquiz.domain.model.Question
import com.openclaw.javaguidesquiz.domain.model.QuestionType
import com.openclaw.javaguidesquiz.domain.model.Scoring
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ScoringTest {
    @Test
    fun singleChoice_shouldPassWhenOptionMatches() {
        val q = Question("1", QuestionType.SINGLE, "Java", "", listOf("A", "B"), listOf("B"), "")
        assertTrue(Scoring.check(q, setOf(1), ""))
        assertFalse(Scoring.check(q, setOf(0), ""))
    }

    @Test
    fun multiChoice_shouldRequireExactSet() {
        val q = Question("2", QuestionType.MULTI, "JVM", "", listOf("A", "B", "C"), listOf("A", "C"), "")
        assertTrue(Scoring.check(q, setOf(0, 2), ""))
        assertFalse(Scoring.check(q, setOf(0), ""))
    }

    @Test
    fun blank_shouldIgnoreCaseAndSpaces() {
        val q = Question("3", QuestionType.BLANK, "MySQL", "", answers = listOf("REPEATABLE READ"), explanation = "")
        assertTrue(Scoring.check(q, emptySet(), "repeatable read"))
        assertTrue(Scoring.check(q, emptySet(), " REPEATABLE   READ "))
    }

    @Test
    fun blank_shouldNormalizeChineseAndEnglishPunctuation() {
        val q = Question("4", QuestionType.BLANK, "Network", "", answers = listOf("A,B."), explanation = "")
        assertTrue(Scoring.check(q, emptySet(), "a，b。"))
        assertFalse(Scoring.check(q, emptySet(), "a,b!"))
    }

    @Test
    fun multiChoice_shouldFailWhenContainsExtraOrInvalidOption() {
        val q = Question("5", QuestionType.MULTI, "JVM", "", listOf("A", "B", "C"), listOf("A", "C"), "")
        assertFalse(Scoring.check(q, setOf(0, 1, 2), ""))
        assertFalse(Scoring.check(q, setOf(0, 3), ""))
    }
}
