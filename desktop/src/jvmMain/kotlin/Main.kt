
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.irack.stonemanager.App
import io.github.irack.stonemanager.bluetooth.BTService
import io.github.irack.stonemanager.resource.LString
import io.github.irack.stonemanager.ui.widget.ColorPicker
import io.github.irack.stonemanager.util.getPlatform


fun main() = application {
    val platform = getPlatform().name
    val icon = when {
        platform.contains("Windows") -> painterResource("icon.ico")
        platform.contains("Mac") -> painterResource("icon.icns")
        platform.contains("Linux") -> painterResource("icon.png")
        else -> null
    }

    val service = BTService()
    service.start()

    Window(
        onCloseRequest = ::exitApplication,
        title = LString.appName,
        icon = icon,
        undecorated = false
    ) {
        App(onClick = {
            service.broadcastCommand()
        })
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
