package io.github.irack.stonemanager.`interface`.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.irack.stonemanager.`interface`.ui.theme.colorDark
import io.github.irack.stonemanager.`interface`.ui.theme.colorLight
import io.github.irack.stonemanager.`interface`.ui.theme.shapes
import io.github.irack.stonemanager.`interface`.ui.theme.typography


@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) colorDark else colorLight
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}
