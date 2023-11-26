import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.irack.stonemanager.App
import io.github.irack.stonemanager.resource.LString

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = LString.appName,
        icon = null,
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
