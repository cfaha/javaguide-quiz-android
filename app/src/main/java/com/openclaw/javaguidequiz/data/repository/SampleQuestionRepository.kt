package com.openclaw.javaguidesquiz.data.repository

import android.content.Context
import com.openclaw.javaguidesquiz.domain.model.Question
import com.openclaw.javaguidesquiz.domain.model.QuestionType
import org.json.JSONArray

object SampleQuestionRepository {

    fun load(context: Context): List<Question> {
        return try {
            val json = context.assets.open("questions.json").bufferedReader().use { it.readText() }
            parseQuestions(json)
        } catch (_: Exception) {
            fallback()
        }
    }

    private fun parseQuestions(json: String): List<Question> {
        val arr = JSONArray(json)
        val out = ArrayList<Question>(arr.length())
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            val type = when (o.optString("type").lowercase()) {
                "single" -> QuestionType.SINGLE
                "multi" -> QuestionType.MULTI
                "blank" -> QuestionType.BLANK
                else -> QuestionType.SINGLE
            }
            val options = mutableListOf<String>()
            val optionsArr = o.optJSONArray("options") ?: JSONArray()
            for (j in 0 until optionsArr.length()) options += optionsArr.optString(j)

            val answers = mutableListOf<String>()
            val answersArr = o.optJSONArray("answers") ?: JSONArray()
            for (j in 0 until answersArr.length()) answers += answersArr.optString(j)

            out += Question(
                id = o.optString("id", "q_$i"),
                type = type,
                category = o.optString("category", "未分类"),
                stem = o.optString("stem", ""),
                options = options,
                answers = answers.ifEmpty { listOf("选项A") },
                explanation = o.optString("explanation", ""),
                difficulty = o.optInt("difficulty", 2)
            )
        }
        return out
    }

    private fun fallback(): List<Question> = listOf(
        Question(
            id = "java_001",
            type = QuestionType.SINGLE,
            category = "Java基础",
            stem = "Java 中参数传递本质是？",
            options = listOf("引用传递", "值传递", "按需传递", "随机传递"),
            answers = listOf("值传递"),
            explanation = "Java 只有值传递；对象传递的是引用副本。",
            difficulty = 1
        )
    )
}
