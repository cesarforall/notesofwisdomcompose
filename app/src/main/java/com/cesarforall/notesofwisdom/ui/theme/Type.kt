package com.cesarforall.notesofwisdom.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cesarforall.notesofwisdom.R

val SourceSerif4 = FontFamily(Font(R.font.source_serif4))

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = SourceSerif4,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)