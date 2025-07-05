package com.cesarforall.notesofwisdom.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WhiteBlackColorScheme = lightColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    primaryContainer = Color.White,
    onPrimaryContainer = Color.Black
)

@Composable
fun NotesOfWisdomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WhiteBlackColorScheme,
        typography = Typography,
        content = content
    )
}