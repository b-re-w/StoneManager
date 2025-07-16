package io.github.irack.stonemanager.`interface`.ui.view.main

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.abhilash.apps.composecolorpicker.argbToHsv
import com.abhilash.apps.composecolorpicker.rememberForeverScrollState
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain
import io.github.irack.stonemanager.`interface`.resource.localization.LS
import io.github.irack.stonemanager.`interface`.resource.util.convertPixelsToDp
import io.github.irack.stonemanager.`interface`.ui.style.NeuPulsateEffectFlatButton
import io.github.irack.stonemanager.`interface`.ui.theme.*
import io.github.irack.stonemanager.`interface`.ui.unit.toList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.skia.paragraph.HeightMode
import kotlin.math.ceil


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
    schedulingEnabled: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    reconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("07:00")},
    disconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("23:00")},
) {
    val haptic = LocalHapticFeedback.current

    val scrollState = rememberForeverScrollState("MainAppViewLayout")
    val innerScrollStateStart = rememberForeverScrollState("MainAppViewLayoutStart")
    val innerScrollStateEnd = rememberForeverScrollState("MainAppViewLayoutEnd")
    val isViewLaunched = rememberSaveable { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val showBottomSheet = remember { mutableStateOf(false) }
    val openLampTypeSelector: () -> Unit = {
        scope.launch {
            dialogOpeningHaptic({
                haptic.performHapticFeedback(it)
            }) {
                delay(200)
                showBottomSheet.value = true
                delay(100)
            }
        }
    }
    ListSelectDialog(showBottomSheet, lampType)
    val openWelcomeLightTypeSelector: () -> Unit = {

    }
    val openReconnectTimeSelector: () -> Unit = {
        scope.launch {
            dialogOpeningHaptic({
                haptic.performHapticFeedback(it)
            }) {
                delay(400)
            }
        }
    }
    val openDisconnectTimeSelector: () -> Unit = {
        scope.launch {
            dialogOpeningHaptic({
                haptic.performHapticFeedback(it)
            }) {
                delay(400)
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
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .verticalScroll(innerScrollStateStart)
                        .padding(20.dp)
                        .systemBarsPadding()
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MainHeader()
                    MainDevicePanel(
                        isViewLaunched.value,
                        deviceName = deviceName
                    )
                    MainFooter(isViewLaunched.value)
                }
                Column(
                    Modifier
                        .fillMaxHeight()
                        .verticalScroll(innerScrollStateEnd)
                        .padding(20.dp, 26.dp, 20.dp, 26.dp)
                        .systemBarsPadding()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    MainTitlePanel(isViewLaunched.value, Modifier.fillMaxWidth().padding(8.dp, 12.dp, 8.dp, 32.dp))
                    MainControlPanel(
                        isViewLaunched,
                        initialHSV, hsv, brightness, lampType,
                        openLampTypeSelector, openWelcomeLightTypeSelector,
                        schedulingEnabled, reconnectTimeString, disconnectTimeString,
                        openReconnectTimeSelector, openDisconnectTimeSelector
                    )
                }
            }
        } else {
            val currentHeight = rememberSaveable { mutableStateOf(0) }
            val verticalSpacerHeight = animateDpAsState(
                convertPixelsToDp(currentHeight.value.toFloat()),
                animationSpec = tween(durationMillis = 1000, delayMillis = 100)
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(20.dp)
                    .systemBarsPadding(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
                    MainHeader()
                    Box(Modifier.fillMaxWidth()) {
                        Box(Modifier.fillMaxWidth().onGloballyPositioned { currentHeight.value = it.size.height }
                        ) {
                            MainTitlePanel(isViewLaunched.value)
                        }
                        Spacer(Modifier.height(verticalSpacerHeight.value))
                    }
                }
                MainDevicePanel(
                    isViewLaunched.value,
                    deviceName = deviceName
                )
                Column(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSelectDialog(
    showBottomSheet: MutableState<Boolean> = remember { mutableStateOf(false) },
    selectionState: MutableState<Int> = remember { mutableStateOf(0) },
    targetStringList: List<String> = LS.mainLampStatus.toList(),
    targetIconList: List<@Composable (String, Modifier) -> Unit> = DrawableMain.SelectDialog.lampTypeIconList,
    title: @Composable () -> Unit = {
        val lampText = LS.mainLampDescriptor
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            DrawableMain.SelectDialog.Lamp(lampText, Modifier.size(30.dp))
            HeadText(lampText, fontWeight = FontWeight.ExtraBold)
        }
    }
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            sheetState = sheetState
        ) {
            Surface(Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp)) {
                title()
            }

            BoxWithConstraints(Modifier.fillMaxWidth()) {
                val columnLength = if (isHorizontal()) 4 else 2
                Column(Modifier.fillMaxWidth(), Arrangement.spacedBy(20.dp)) {
                    for (i in 0 until ceil(targetStringList.size/columnLength.toDouble()).toInt()) {
                        Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp), Arrangement.spacedBy(20.dp)) {
                            for (j in 0 until columnLength) {
                                val item = i * columnLength + j
                                val desc = targetStringList.getOrNull(item)
                                if (desc != null) {
                                    NeuPulsateEffectFlatButton(
                                        onClick = {
                                            selectionState.value = item
                                        },
                                        modifier = Modifier.weight(1f),
                                        contentPadding = PaddingValues(4.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            if (selectionState.value == item) MaterialTheme.colorScheme.onTertiary
                                            else appColorSet.listSelectionButtonBackground)
                                    ) {
                                        Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
                                            targetIconList[item](desc, Modifier.padding(10.dp).size(48.dp))
                                            BodyText(desc, Modifier.padding(end = 12.dp), fontSize = headTextFontSize, color
                                                = if (selectionState.value == item) MaterialTheme.colorScheme.tertiary
                                                    else appColorSet.listSelectionButtonForeground,
                                                resizer = remember { mutableStateOf(1f) })
                                        }
                                    }
                                } else {
                                    Box(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

            Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                NeuPulsateEffectFlatButton(
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet.value = false
                            }
                        }
                    },
                    modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 30.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onTertiary),
                    contentPadding = PaddingValues(24.dp, 16.dp)
                ) {
                    StatusText("Close", color = MaterialTheme.colorScheme.tertiary)
                }
            }

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
fun TimeSelectDialog() {

}

@Composable
fun LicenseDialog(showLicenseDialog: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }) {
    if (showLicenseDialog.value) {
        AlertDialog(
            onDismissRequest = { showLicenseDialog.value = false },
            title = { Text("라이센스 목록") },
            text = { Text("1. JetBrains Compose Multiplatform\n" +
                    "라이브러리: compose.runtime, compose.ui, compose.foundation, compose.materialIconsExtended, compose.material3\n" +
                    "\n" +
                    "저작권: (C) 2020- JetBrains s.r.o. and contributors\n" +
                    "\n" +
                    "라이선스: Apache License 2.0\n" +
                    "\n" +
                    "2. kotlinx.serialization\n" +
                    "라이브러리: kotlin.serialization\n" +
                    "\n" +
                    "저작권: (C) 2017-2024 JetBrains s.r.o. and contributors\n" +
                    "\n" +
                    "라이선스: Apache License 2.0\n" +
                    "\n" +
                    "3. Skiko\n" +
                    "라이브러리: skiko.common\n" +
                    "\n" +
                    "저작권: (C) 2021 JetBrains s.r.o.\n" +
                    "\n" +
                    "라이선스: Apache License 2.0\n" +
                    "\n" +
                    "4. MOKO Resources\n" +
                    "라이브러리: moko.resources\n" +
                    "\n" +
                    "저작권: (C) 2019 IceRock MAG Inc.\n" +
                    "\n" +
                    "라이선스: Apache License 2.0\n" +
                    "\n" +
                    "5. km-logging\n" +
                    "라이브러리: kmlogging\n" +
                    "\n" +
                    "저작권: (C) 2021 GitHub contributors (Touchlab)\n" +
                    "\n" +
                    "라이선스: Apache License 2.0\n" +
                    "\n" +
                    "6. ComposeColorPicker\n" +
                    "프로젝트 모듈: :lib:ComposeColorPicker:colorpicker\n" +
                    "\n" +
                    "저작권: 해당 모듈의 오픈소스 프로젝트 또는 직접 개발 여부에 따라 상이\n" +
                    "\n" +
                    "라이선스: 적용된 오픈소스 라이선스(예시: MIT, Apache 2.0 등)\n" +
                    "\n" +
                    "[출처/저작권 확인 필요]\n" +
                    "\n" +
                    "7. FilledSliderCompose\n" +
                    "프로젝트 모듈: :lib:FilledSliderCompose:filled-slider-compose\n" +
                    "\n" +
                    "저작권: 해당 모듈의 오픈소스 프로젝트 또는 직접 개발 여부에 따라 상이\n" +
                    "\n" +
                    "라이선스: 적용된 오픈소스 라이선스(예시: MIT, Apache 2.0 등)\n" +
                    "\n" +
                    "[출처/저작권 확인 필요]\n" +
                    "\n" +
                    "8. compose-neumorphism\n" +
                    "프로젝트 모듈: :lib:compose-neumorphism:neumorphic\n" +
                    "\n" +
                    "저작권: 해당 모듈의 오픈소스 프로젝트 또는 직접 개발 여부에 따라 상이\n" +
                    "\n" +
                    "라이선스: 적용된 오픈소스 라이선스(예시: MIT, Apache 2.0 등)\n" +
                    "\n") },
            confirmButton = {
                TextButton(onClick = { showLicenseDialog.value = false }) {
                    Text("닫기")
                }
            },
            dismissButton = null,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        )
    }
}
