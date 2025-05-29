package io.github.irack.stonemanager.`interface`.ui.view.main

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhilash.apps.composecolorpicker.argbToHsv
import io.github.irack.stonemanager.`interface`.resource.localization.LS
import io.github.irack.stonemanager.`interface`.resource.util.convertPixelsToDp
import io.github.irack.stonemanager.`interface`.ui.style.*
import io.github.irack.stonemanager.`interface`.ui.theme.BodyText
import io.github.irack.stonemanager.`interface`.ui.theme.HeadText
import io.github.irack.stonemanager.`interface`.ui.theme.StatusText
import io.github.irack.stonemanager.`interface`.ui.view.common.Footer
import io.github.irack.stonemanager.`interface`.ui.view.common.Header
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain
import io.github.irack.stonemanager.`interface`.ui.style.NeuPulsateEffectFlatButton

@Composable
fun MainHeader(paddingValues: PaddingValues = PaddingValues(0.dp, 6.dp, 0.dp, 26.dp)) {
    Header(paddingValues)
}

@Composable
fun MainTitlePanel(isViewLaunched: Boolean = false, modifier: Modifier = Modifier.fillMaxWidth().padding(8.dp, 12.dp)) {
    val showState = rememberSaveable { mutableStateOf(false) }
    val isEnabled = rememberSaveable { mutableStateOf(false) }

    if (isEnabled.value) {
        Column(modifier, Arrangement.spacedBy(12.dp)) {
            AnimatedVisibility(showState.value,
                enter = fadeInSlideInHorizontally(400, 1500, false),
                exit = fadeOutSlideOutHorizontally(400, 0, false)
            ) {
                HeadText("Welcome, User", color = MaterialTheme.colorScheme.onBackground)
            }

            AnimatedVisibility(showState.value,
                enter = fadeInSlideInHorizontally(400, 1800, false),
                exit = fadeOutSlideOutHorizontally(400, 200, false)
            ) {
                HeadText("Have a good day!", color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 32.sp, maxLines = 2, lineHeight = 32.sp)
            }
        }
    }

    val scope = rememberCoroutineScope()
    scope.launch {
        if (!isViewLaunched) {
            isEnabled.value = !isViewLaunched
            delay(1000)
            showState.value = !isViewLaunched
        } else {
            showState.value = !isViewLaunched
            delay(1000)
            isEnabled.value = !isViewLaunched
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDevicePanel(
    isViewLaunched: Boolean = false,
    modifier: Modifier = Modifier,
    deviceName: MutableState<String> = rememberSaveable { mutableStateOf("") },
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    // BottomSheet 상태와 표시 플래그
    val sheetState = rememberModalBottomSheetState()
    val showDeviceSheet = rememberSaveable { mutableStateOf(false) }

    Column(modifier, verticalArrangement, horizontalAlignment) {
        VolumeWheelDial(isViewLaunched)

        AnimatedVisibility(showDeviceSheet.value || isViewLaunched) {
            Column(
                Modifier
                    .padding(bottom = 20.dp),
                verticalArrangement,
                horizontalAlignment
            ) {
                BodyText(
                    text = LS.mainConnectedDevice,
                    modifier = Modifier
                        .clickable { showDeviceSheet.value = true }
                        .padding(4.dp)
                )
                StatusText(
                    text = deviceName.value.ifEmpty { LS.mainDeviceConnectionStatus }
                )
            }
        }
    }

    LaunchedEffect(isViewLaunched) {
        if (isViewLaunched) {
            delay(2000)
        }
    }

    // 실제 BottomSheet 띄우기
    if (showDeviceSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showDeviceSheet.value = false },
            sheetState = sheetState
        ) {
            Surface(Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableMain.SelectDialog.Lamp(
                        LS.mainConnectedDevice,
                        Modifier.size(30.dp)
                    )
                    HeadText(
                        LS.mainConnectedDevice,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BodyText("기기 이름: ${deviceName.value.ifEmpty { "미연결" }}")
                BodyText("MAC 주소: AA:BB:CC:DD:EE:FF")
                BodyText("최근 연결 시각: 2025-05-28 09:42")
                BodyText(" ")
                BodyText(" ")
                BodyText(" ")
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
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val showStartButton: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val isStartButtonEnabled: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val showControlPanel: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val startButtonHeight: MutableState<Int> = rememberSaveable { mutableStateOf(0) }
    val verticalSpacerHeight = animateDpAsState(
        convertPixelsToDp(startButtonHeight.value.toFloat()),
        animationSpec = tween(durationMillis = 1000, delayMillis = 100)
    )

    Box {
        Box(Modifier.onGloballyPositioned { startButtonHeight.value = it.size.height }
        ) {
            val (startButtonState, startButtonTransition, setStartButtonTransitState)
                = createNeuAnimation(3000, 0)
            setStartButtonTransitState(showStartButton.value)
            if (isStartButtonEnabled.value) {
                AnimatedVisibility(showStartButton.value, enter = startButtonTransition.first, exit = fadeOut()) {
                    val scope = rememberCoroutineScope()
                    NeuPulsateEffectFlatButton(
                        onClick = {
                            showStartButton.value = false
                            scope.launch {
                                delay(250)
                                isViewLaunched.value = true
                            }
                        },
                        modifier = Modifier.padding(vertical = 20.dp),
                        contentPadding = PaddingValues(24.dp, 16.dp),
                        shadowElevation = startButtonState.value.dp
                    ) {
                        StatusText(text = "Get Started")
                    }
                }
            }
        }
        Spacer(Modifier.height(verticalSpacerHeight.value))
    }

    rememberCoroutineScope().launch {
        if (!isViewLaunched.value) {
            isStartButtonEnabled.value = !isViewLaunched.value
            delay(1000)
            showStartButton.value = !isViewLaunched.value
        } else {
            showStartButton.value = !isViewLaunched.value
            delay(800)
            isStartButtonEnabled.value = !isViewLaunched.value
            delay(200)
            showControlPanel.value = isViewLaunched.value
        }
    }

    val spacer = animateDpAsState(if (lampType.value == 0) 8.dp else 16.dp)
    Column(modifier, Arrangement.spacedBy(spacer.value), Alignment.CenterHorizontally) {
        val (state1, transition1, setter1) = createNeuAnimation(300, 0)
        setter1(showControlPanel.value)
        AnimatedVisibility(showControlPanel.value, enter = transition1.first, exit = transition1.second) {
            ControlMenuBar(lampType, openLampTypeSelector, openWelcomeLightTypeSelector, shadowElevation = state1.value.dp)
        }
        val (state2, transition2, setter2) = createNeuAnimation(800, 0)
        setter2(showControlPanel.value)
        AnimatedVisibility(showControlPanel.value, enter = transition2.first, exit = transition2.second) {
            LampControlSpace(lampType.value, initialHSV, hsv, brightness, shadowElevation = state2.value.dp)
        }
        val (state3, transition3, setter3) = createNeuAnimation(1200, 0)
        setter3(showControlPanel.value)
        AnimatedVisibility(showControlPanel.value, enter = transition3.first, exit = transition3.second) {
            ReconnectionScheduleButton(schedulingEnabled, reconnectTimeString, disconnectTimeString,
                openReconnectTimeSelector, openDisconnectTimeSelector, state3.value.dp)
            SideEffect {

            }
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
