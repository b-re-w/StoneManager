package io.github.irack.stonemanager.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val colorLight = lightColorScheme(
    primary = Color(0xFFF4F8FE),
    onPrimary = Color(0xFF6E7C96),
    secondary = Color(0xFF03DAC5),
    tertiary = Color(0xFF3700B3),
    background = Color(0xFFE9F1FD),
    onBackground = Color(0xFFA2B0C9)
)

val colorDark = darkColorScheme(
    primary = Color(0xFF25262B),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF03DAC5),
    tertiary = Color(0xFF3700B3),
    background = Color(0xFF1A1C20),
    onBackground = Color(0xFF767171)
)

abstract class AppColorSet {
    abstract val lightShadow: Color
    abstract val darkShadow : Color
}

object LightColorSet: AppColorSet() {
    override val lightShadow = Color(0xF0FFFFFF)
    override val darkShadow = Color(0x4F2D2670)
}

object DarkColorSet: AppColorSet() {
    override val lightShadow = Color(0x66494949)
    override val darkShadow = Color(0x66000000)
}

val appColorSet
    @Composable get() = if (MaterialTheme.colors.isLight) LightColorSet else DarkColorSet
