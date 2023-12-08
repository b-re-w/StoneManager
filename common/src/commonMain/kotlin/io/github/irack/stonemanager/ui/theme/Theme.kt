package io.github.irack.stonemanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import io.github.irack.stonemanager.util.isAfter6AMBefore6PM


@Composable
expect fun AppTheme(
    darkTheme: Boolean = /*isTimeInNightPeriod()*/isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
)

/*
 * @return true when nighttime or if dark theme is enabled
 */
@Composable
fun isTimeInNightPeriod(): Boolean = !isAfter6AMBefore6PM() || isSystemInDarkTheme()
