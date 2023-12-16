package io.github.irack.stonemanager.`interface`.resource.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun convertPixelsToDp(pixels: Float): Dp {
    val density = LocalDensity.current.density
    return with(LocalDensity.current) {
        (pixels / density).dp
    }
}

@Composable
fun convertDpToPixels(dp: Dp): Float {
    val density = LocalDensity.current.density
    return with(LocalDensity.current) {
        dp.value * density
    }
}
