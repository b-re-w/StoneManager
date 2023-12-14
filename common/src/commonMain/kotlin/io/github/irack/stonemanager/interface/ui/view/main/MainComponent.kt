package io.github.irack.stonemanager.`interface`.ui.view.main

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhilash.apps.composecolorpicker.argbToHsv
import io.github.irack.stonemanager.`interface`.resource.localization.LS
import io.github.irack.stonemanager.`interface`.ui.style.NeuPulsateEffectFlatButton
import io.github.irack.stonemanager.`interface`.ui.style.createNeuAnimation
import io.github.irack.stonemanager.`interface`.ui.style.rememberForeverNeuAnimation
import io.github.irack.stonemanager.`interface`.ui.theme.BodyText
import io.github.irack.stonemanager.`interface`.ui.theme.StatusText
import io.github.irack.stonemanager.`interface`.ui.theme.defaultNeuElevation
import io.github.irack.stonemanager.`interface`.ui.view.common.Footer
import io.github.irack.stonemanager.`interface`.ui.view.common.Header
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
    disconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("23:00")},
    openReconnectTimeSelector: () -> Unit = {},
    openDisconnectTimeSelector: () -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth(),
    isHorizontalLayout: Boolean = false
) {
    val showStartButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val isInitialized: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }

    val (startButtonState, startButtonTransition, setStartButtonTransitState) = createNeuAnimation(0, 0)
    setStartButtonTransitState(showStartButton.value)
    if (!isViewLaunched.value) {
        AnimatedVisibility(showStartButton.value, enter = startButtonTransition.first, exit = startButtonTransition.second) {
            val scope = rememberCoroutineScope()
            NeuPulsateEffectFlatButton(
                onClick = {
                    showStartButton.value = false
                    scope.launch {
                        delay(250)
                        isViewLaunched.value = true
                    }
                },
                contentPadding = PaddingValues(24.dp, 16.dp),
                shadowElevation = startButtonState.value.dp
            ) {
                StatusText(text = "Get Started")
            }
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

    Column(modifier, Arrangement.spacedBy(16.dp), Alignment.CenterHorizontally) {
        val (state1, transition1, setter1) = createNeuAnimation(300, 0)
        setter1(isViewLaunched.value)
        AnimatedVisibility(isViewLaunched.value, enter = transition1.first, exit = transition1.second) {
            ReconnectionScheduleButton(schedulingEnabled, reconnectTimeString, disconnectTimeString,
                openReconnectTimeSelector, openDisconnectTimeSelector, state1.value.dp)
            SideEffect {

            }
        }
        val (state2, transition2, setter2) = createNeuAnimation(800, 0)
        setter2(isViewLaunched.value)
        AnimatedVisibility(isViewLaunched.value, enter = transition2.first, exit = transition2.second) {
            ControlMenuBar(lampType, openLampTypeSelector, openWelcomeLightTypeSelector, shadowElevation = state2.value.dp)
        }
        val (state3, transition3, setter3) = createNeuAnimation(1200, 0)
        setter3(isViewLaunched.value)
        AnimatedVisibility(isViewLaunched.value, enter = transition3.first, exit = transition3.second) {
            LampControlSpace(lampType.value, initialHSV, hsv, brightness, shadowElevation = state3.value.dp)
        }
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
