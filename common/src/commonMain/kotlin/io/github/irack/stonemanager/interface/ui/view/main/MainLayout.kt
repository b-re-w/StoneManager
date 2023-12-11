package io.github.irack.stonemanager.`interface`.ui.view.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.abhilash.apps.composecolorpicker.argbToHsv
import com.abhilash.apps.composecolorpicker.rememberForeverScrollState
import io.github.irack.stonemanager.`interface`.ui.theme.dialogOpeningHaptic
import io.github.irack.stonemanager.`interface`.ui.theme.isHorizontal
import kotlinx.coroutines.launch


@Composable
fun MainLayout(
    initialHSV: FloatArray = argbToHsv(Color.Cyan.toArgb()),
    hsv: MutableState<Triple<Float, Float, Float>> = rememberSaveable {
        mutableStateOf(
            Triple(initialHSV[0], initialHSV[1], initialHSV[2])
        )
    },
    deviceName: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    },
    brightness: MutableState<Float> = rememberSaveable {
        mutableStateOf(0.5f)
    },
    lampType: MutableState<Int> = rememberSaveable {
        mutableStateOf(0)
    },
    schedulingEnabled: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    reconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("07:00")},
    disconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("32:00")},
) {
    val haptic = LocalHapticFeedback.current

    val scrollState = rememberForeverScrollState("MainAppViewLayout")
    val innerScrollState = rememberForeverScrollState("MainAppViewLayoutEnd")
    val isViewLaunched = rememberSaveable { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val openLampTypeSelector: () -> Unit = {

    }
    val openWelcomeLightTypeSelector: () -> Unit = {

    }
    val openReconnectTimeSelector: () -> Unit = {
        scope.launch {
            dialogOpeningHaptic {
                haptic.performHapticFeedback(it)
            }
        }
    }
    val openDisconnectTimeSelector: () -> Unit = {
        scope.launch {
            dialogOpeningHaptic {
                haptic.performHapticFeedback(it)
            }
        }
    }

    val quantizedBrightness = remember(brightness) {
        derivedStateOf { (brightness.value * 10).toInt() }
    }
    LaunchedEffect(quantizedBrightness.value) {
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }
    val quantizedHsv = remember(hsv) {
        derivedStateOf { (hsv.value.first/10 + (hsv.value.second+hsv.value.third)*10).toInt() }
    }
    LaunchedEffect(quantizedHsv.value) {
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        if (isHorizontal()) {
            Row(
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .padding(20.dp)
                        .systemBarsPadding()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MainHeader()
                    MainDevicePanel(
                        isViewLaunched.value,
                        Modifier.fillMaxHeight(),
                        deviceName
                    )
                    MainFooter(isViewLaunched.value)
                }
                Column(
                    Modifier
                        .fillMaxHeight()
                        .verticalScroll(innerScrollState)
                        .padding(20.dp, 26.dp, 20.dp, 26.dp)
                        .systemBarsPadding()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MainTitlePanel(isViewLaunched.value)
                    Row(Modifier.fillMaxWidth(), ) {
                        MainControlPanel(
                            isViewLaunched,
                            initialHSV, hsv, brightness, lampType,
                            openLampTypeSelector, openWelcomeLightTypeSelector,
                            schedulingEnabled, reconnectTimeString, disconnectTimeString,
                            openReconnectTimeSelector, openDisconnectTimeSelector
                        )
                    }
                }
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(20.dp)
                    .systemBarsPadding(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainHeader()
                MainTitlePanel(isViewLaunched.value)
                MainDevicePanel(
                    isViewLaunched.value,
                    Modifier.fillMaxHeight(),
                    deviceName
                )
                MainControlPanel(
                    isViewLaunched,
                    initialHSV, hsv, brightness, lampType,
                    openLampTypeSelector, openWelcomeLightTypeSelector,
                    schedulingEnabled, reconnectTimeString, disconnectTimeString,
                    openReconnectTimeSelector, openDisconnectTimeSelector
                )
                MainFooter(isViewLaunched.value)
            }
        }
    }
}

@Composable
fun ListSelectDialog() {
//    val animateTrigger = remember { mutableStateOf(false) }
//    LaunchedEffect(key1 = Unit) {
//        launch {
//            delay(DIALOG_BUILD_TIME)
//            animateTrigger.value = true
//        }
//    }
//    Dialog(onDismissRequest = onDismissRequest) {
//        Box(contentAlignment = contentAlignment,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            AnimatedScaleInTransition(visible = animateTrigger.value) {
//                content()
//            }
//        }
//    }
}

@Composable
fun TimeSelectDialog() {

}

@Composable
fun LicenseDialog() {

}
