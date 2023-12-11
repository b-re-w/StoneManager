package io.github.irack.stonemanager.ui.view.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Vertices
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.abhilash.apps.composecolorpicker.HueBar
import com.abhilash.apps.composecolorpicker.SaturationValuePanel
import com.abhilash.apps.composecolorpicker.argbToHsv
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import io.github.irack.stonemanager.resource.localization.LS
import io.github.irack.stonemanager.ui.style.ClickAnimation
import io.github.irack.stonemanager.ui.style.NeuPulsateEffectFlatButton
import io.github.irack.stonemanager.ui.theme.*
import io.github.irack.stonemanager.ui.view.Footer
import io.github.irack.stonemanager.ui.view.Header
import io.github.irack.stonemanager.unit.toList
import io.github.irack.stonemanager.util.getPlatform
import io.github.seyoungcho2.slider.FilledSlider
import io.github.seyoungcho2.slider.model.SliderColor
import io.github.seyoungcho2.slider.model.SliderOrientation
import io.github.seyoungcho2.slider.model.SliderType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MainHeader(paddingValues: PaddingValues = PaddingValues(0.dp, 6.dp, 0.dp, 26.dp)) {
    Header(paddingValues)
}

@Composable
fun MainTitlePanel(isViewLaunched: Boolean = false) {

}

@Composable
fun MainDevicePanel(
    isViewLaunched: Boolean = false,
    modifier: Modifier = Modifier,
    deviceName: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    },
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    Column(modifier, verticalArrangement, horizontalAlignment) {
        VolumnWheelDial(isViewLaunched)
        AnimatedVisibility(isViewLaunched) {
            Column(Modifier, verticalArrangement, horizontalAlignment) {
                BodyText(LS.mainConnectedDevice)
                StatusText(if (deviceName.value == "") LS.mainDeviceConnectionStatus else deviceName.value)
            }
        }
    }
}

@Composable
fun MainControlPanel(
    isViewLaunched: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    initialHSV: FloatArray = argbToHsv(Color.Cyan.toArgb()),
    hsv: MutableState<Triple<Float, Float, Float>> = rememberSaveable {
        mutableStateOf(
            Triple(initialHSV[0], initialHSV[1], initialHSV[2])
        )
    },
    brightness: MutableState<Float> = rememberSaveable {
        mutableStateOf(0.5f)
    },
    lampType: MutableState<Int> = rememberSaveable {
        mutableStateOf(0)
    },
    openLampTypeSelector: () -> Unit = {},
    openWelcomeLightTypeSelector: () -> Unit = {},
    schedulingEnabled: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    reconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("07:00")},
    disconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("32:00")},
    openReconnectTimeSelector: () -> Unit = {},
    openDisconnectTimeSelector: () -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth(),
    isHorizontalLayout: Boolean = false
) {
    val showStartButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val isInitialized: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    Column(modifier, Arrangement.spacedBy(16.dp), Alignment.CenterHorizontally) {
        AnimatedVisibility(isViewLaunched.value) {
            ControlMenuBar(lampType, openLampTypeSelector, openWelcomeLightTypeSelector)
        }
        AnimatedVisibility(isViewLaunched.value) {
            LampControlSpace(lampType.value == 1, initialHSV, hsv, brightness)

        }
        AnimatedVisibility(isViewLaunched.value) {
            ReconnectionScheduleButton(schedulingEnabled, reconnectTimeString, disconnectTimeString,
                openReconnectTimeSelector, openDisconnectTimeSelector)
            SideEffect {

            }
        }
    }

    AnimatedVisibility(showStartButton.value, exit = fadeOut()) {
        NeuPulsateEffectFlatButton(
            onClick = {
                isViewLaunched.value = true
                showStartButton.value = false
            },
            contentPadding = PaddingValues(24.dp, 16.dp)
        ) {
            StatusText(text = "Get Started")
        }
    }
    if (!isInitialized.value) {
        val scope = rememberCoroutineScope()
        scope.launch {
            delay(500)
            showStartButton.value = true
        }
        isInitialized.value = true
    }
}

@Composable
fun MainFooter(isViewLaunched: Boolean = false, paddingValues: PaddingValues = PaddingValues(0.dp, 26.dp, 0.dp, 6.dp)) {
    if (isViewLaunched) {
        Footer(paddingValues)
    } else {
        Spacer(Modifier.padding(paddingValues))
    }
}
