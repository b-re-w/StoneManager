import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.abhilash.apps.composecolorpicker.ColorPicker
import io.github.irack.stonemanager.App
import io.github.irack.stonemanager.`interface`.resource.localization.localizedString
import io.github.irack.stonemanager.`interface`.resource.util.getPlatform


fun main() = application {
    val platform = getPlatform().name
    val icon = when {
        platform.contains("Windows") -> painterResource("icon.ico")
        //platform.contains("Mac") -> painterResource("icon.icns")
        platform.contains("Linux") -> painterResource("icon.png")
        else -> null
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = localizedString.appName,
        icon = icon,
        undecorated = false
    ) {
        App()
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
