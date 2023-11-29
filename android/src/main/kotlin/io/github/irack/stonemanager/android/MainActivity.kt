package io.github.irack.stonemanager.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abhilash.apps.composecolorpicker.ColorPicker
import io.github.irack.stonemanager.App


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    App()
}

@Preview
@Composable
fun ColorPickerPreview() {
    MaterialTheme {
        ColorPicker()
    }
}
