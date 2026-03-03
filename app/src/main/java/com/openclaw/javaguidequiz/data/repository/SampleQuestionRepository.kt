package com.openclaw.javaguidesquiz.data.repository

import com.openclaw.javaguidesquiz.domain.model.Question
import com.openclaw.javaguidesquiz.domain.model.QuestionType

object SampleQuestionRepository {
    fun load(): List<Question> = listOf(
        Question(
            id = "java_001",
            type = QuestionType.SINGLE,
            category = "Java基础",
            stem = "Java 中参数传递本质是？",
            options = listOf("引用传递", "值传递", "按需传递", "随机传递"),
            answers = listOf("值传递"),
            explanation = "Java 只有值传递；对象传递的是引用副本。",
            difficulty = 1
        ),
        Question(
            id = "jvm_001",
            type = QuestionType.MULTI,
            category = "JVM",
            stem = "以下哪些属于 JVM 运行时内存区域？",
            options = listOf("堆", "虚拟机栈", "本地方法栈", "TCP 缓冲区"),
            answers = listOf("堆", "虚拟机栈", "本地方法栈"),
            explanation = "TCP 缓冲区属于操作系统网络栈，不是 JVM 运行时内存区域。",
            difficulty = 2
        ),
        Question(
            id = "mysql_001",
            type = QuestionType.BLANK,
            category = "MySQL",
            stem = "InnoDB 默认事务隔离级别是？",
            answers = listOf("REPEATABLE READ", "可重复读"),
            explanation = "MySQL InnoDB 默认是可重复读（Repeatable Read）。",
            difficulty = 2
        )
    )
}
