package com.openclaw.javaguidesquiz

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclaw.javaguidesquiz.data.local.AppDatabase
import com.openclaw.javaguidesquiz.data.repository.PracticeRepository
import com.openclaw.javaguidesquiz.ui.viewmodel.PracticeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PracticeViewModelPersistenceTest {
    private lateinit var db: AppDatabase
    private lateinit var repository: PracticeRepository
    private lateinit var app: Application

    @Before
    fun setUp() {
        app = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(app, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = PracticeRepository(db.quizDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun favoriteAndWrongBookPersistAcrossViewModelRecreation() = runBlocking {
        val vm1 = PracticeViewModel(app, repository)
        waitForQuestions(vm1)

        vm1.startPractice()
        waitForCurrentQuestion(vm1)
        val currentId = vm1.state.value.current!!.id

        vm1.toggleFavorite()
        waitUntil { currentId in vm1.state.value.favorites }

        vm1.submit()
        waitUntil { currentId in vm1.state.value.wrongBook }

        val vm2 = PracticeViewModel(app, repository)
        waitForQuestions(vm2)

        assertTrue(currentId in vm2.state.value.favorites)
        assertTrue(currentId in vm2.state.value.wrongBook)
    }

    private suspend fun waitForQuestions(vm: PracticeViewModel) {
        waitUntil { vm.state.value.allQuestions.isNotEmpty() }
    }

    private suspend fun waitForCurrentQuestion(vm: PracticeViewModel) {
        waitUntil { vm.state.value.current != null }
    }

    private suspend fun waitUntil(
        timeoutMs: Long = 5_000,
        stepMs: Long = 50,
        condition: () -> Boolean
    ) {
        val start = System.currentTimeMillis()
        while (!condition()) {
            if (System.currentTimeMillis() - start > timeoutMs) {
                throw AssertionError("Condition not met within ${timeoutMs}ms")
            }
            delay(stepMs)
        }
    }
}
