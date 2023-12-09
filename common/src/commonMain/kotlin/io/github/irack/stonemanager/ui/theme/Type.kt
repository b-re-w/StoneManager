package io.github.irack.stonemanager.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import io.github.irack.stonemanager.MR


val suiteFontFamily: FontFamily
    @Composable get() = fontFamilyResource(MR.fonts.SUITE.variable)


val typography
    @Composable get() = Typography(
    bodyMedium = TextStyle(
        fontFamily = suiteFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)
