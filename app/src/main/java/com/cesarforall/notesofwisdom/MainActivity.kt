package com.cesarforall.notesofwisdom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.cesarforall.notesofwisdom.ui.theme.NotesOfWisdomTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView)
            ?.isAppearanceLightStatusBars = true

        setContent {
            NotesOfWisdomTheme {
                NotesApp()
            }
        }
    }
}
