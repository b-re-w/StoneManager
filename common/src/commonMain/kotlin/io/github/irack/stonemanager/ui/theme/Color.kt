package io.github.irack.stonemanager.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val colorLight = lightColorScheme(
    primary = Color(0xFFF4F8FE),
    onPrimary = Color(0xFF6E7C96),
    background = Color(0xFFE9F1FD),
    onBackground = Color(0xFFA2B0C9)
)

val colorDark = darkColorScheme(
    primary = Color(0xFF25262B),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFF1A1C20),
    onBackground = Color(0xFF767171)
)

abstract class AppColorSet {
    abstract val lightShadow: Color
    abstract val darkShadow : Color

    companion object {
        val isLight
            @Composable get() = !isTimeInNightPeriod()

        val currentColorScheme
            @Composable get() = MaterialTheme.colorScheme

        val currentAppColorSet
            @Composable get() = if (isLight) LightColorSet else DarkColorSet
    }
}

object LightColorSet: AppColorSet() {
    override val lightShadow = Color(0xD0FFFFFF)
    override val darkShadow = Color(0x402D2670)
}

object DarkColorSet: AppColorSet() {
    override val lightShadow = Color(0xA6494949)
    override val darkShadow = Color(0xF0000000)
}

val appColorSet
    @Composable get() = AppColorSet.currentAppColorSet
