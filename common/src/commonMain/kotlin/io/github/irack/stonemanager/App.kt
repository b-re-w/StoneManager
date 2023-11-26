package io.github.irack.stonemanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.irack.stonemanager.setting.AppTheme
import io.github.irack.stonemanager.util.Greeting
import io.github.irack.stonemanager.util.Locale

@Composable
fun App() {
    AppTheme {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(Greeting().greet())
                Text(Locale.getCurrentLocale())
            }
        }
    }
}
