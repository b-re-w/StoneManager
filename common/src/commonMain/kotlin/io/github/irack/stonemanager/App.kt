package io.github.irack.stonemanager

import androidx.compose.runtime.Composable
import io.github.irack.stonemanager.`interface`.ui.theme.AppTheme
import io.github.irack.stonemanager.controller.presentation.MainView


@Composable
fun App() {
    AppTheme {
        MainView()
    }
}
