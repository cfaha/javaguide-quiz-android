package com.openclaw.javaguidesquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.openclaw.javaguidesquiz.ui.screen.QuizHomeScreen
import com.openclaw.javaguidesquiz.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface {
                    QuizHomeScreen()
                }
            }
        }
    }
}
