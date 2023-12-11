package io.github.irack.stonemanager.`interface`.ui.view.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhilash.apps.composecolorpicker.HueBar
import com.abhilash.apps.composecolorpicker.SaturationValuePanel
import com.abhilash.apps.composecolorpicker.argbToHsv
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain
import io.github.irack.stonemanager.`interface`.resource.localization.LS
import io.github.irack.stonemanager.`interface`.resource.util.getPlatform
import io.github.irack.stonemanager.`interface`.ui.style.ClickAnimation
import io.github.irack.stonemanager.`interface`.ui.style.NeuPulsateEffectFlatButton
import io.github.irack.stonemanager.`interface`.ui.style.PulsateEffectButton
import io.github.irack.stonemanager.`interface`.ui.theme.*
import io.github.irack.stonemanager.`interface`.ui.unit.toList
import io.github.seyoungcho2.slider.FilledSlider
import io.github.seyoungcho2.slider.model.SliderColor
import io.github.seyoungcho2.slider.model.SliderOrientation
import io.github.seyoungcho2.slider.model.SliderType
import kotlinx.coroutines.launch


@Composable
fun VolumnWheelDial(
    isViewLaunched: Boolean = false
) {

}










@Composable
fun ControlMenuBar(
    lampType: MutableState<Int> = rememberSaveable {
        mutableStateOf(0)
    },
    openLampTypeSelector: () -> Unit = {},
    openWelcomeLightTypeSelector: () -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth(),
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(6.dp),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
) {
    Row(modifier, horizontalArrangement, verticalAlignment) {
        NeuPulsateEffectFlatButton(openLampTypeSelector, Modifier.weight(1f)) {
            BodyText(LS.mainLampDescriptor)
            val statusString = LS.mainLampStatus.toList()
            StatusText(
                text = statusString.getOrElse(lampType.value) { statusString[0] },
                overflow = TextOverflow.Ellipsis
            )
        }
        NeuPulsateEffectFlatButton({}, Modifier.weight(1.2f)) {
            BodyText(LS.mainBatteryDescriptor)
            StatusText("100%", overflow = TextOverflow.Ellipsis)  // TODO: Battery Status
        }
        NeuPulsateEffectFlatButton(openWelcomeLightTypeSelector, Modifier.weight(1.4f)) {
            BodyText(LS.mainOnReconnectDescriptor)
            StatusText(LS.mainOnReconnectStatus1, overflow = TextOverflow.Ellipsis)
        }
    }
}


@Composable
fun LampControlSpace(
    hideColorPicker: Boolean = false,
    initialHSV: FloatArray = argbToHsv(Color.Cyan.toArgb()),
    hsv: MutableState<Triple<Float, Float, Float>> = rememberSaveable {
        mutableStateOf(
            Triple(initialHSV[0], initialHSV[1], initialHSV[2])
        )
    },
    brightness: MutableState<Float> = rememberSaveable {
        mutableStateOf(0.5f)
    },
    modifier: Modifier = Modifier.fillMaxWidth(),
    animation: ClickAnimation = ClickAnimation(1f, 0.985f),
    columnSpacedBy: Dp = 10.dp
) {
    val buttonContentsPadding = PaddingValues(0.dp)
    Column(modifier, Arrangement.spacedBy(columnSpacedBy), Alignment.CenterHorizontally) {
        NeuPulsateEffectFlatButton(
            onClick = {},
            modifier = modifier,
            animation = animation,
            contentPadding = buttonContentsPadding
        ) {
            FilledSlider(
                modifier = Modifier
                    .height(colorPickerWidgetHeight)
                    .fillMaxWidth(),
                sliderShape = defaultCornerRoundShape,
                isEnabled = true,
                sliderColor = SliderColor(
                    enabledTrackColor = MaterialTheme.colorScheme.primary,
                    enabledIndicationColor = Color.White
                ),
                sliderOrientation = SliderOrientation.Horizontal,
                sliderType = SliderType.Continuous,
                dragSensitivity = if (getPlatform().name.contains("Desktop")) 2.2f else 1.6f,
                valueRange = 0f..1f,
                currentValue = brightness.value,
                setCurrentValue = { newValue ->
                    brightness.value = newValue
                }
            )
        }

        AnimatedVisibility(hideColorPicker) {
            NeuPulsateEffectFlatButton(
                onClick = {},
                modifier = modifier,
                animation = animation,
                contentPadding = buttonContentsPadding
            ) {
                HueBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    height = colorPickerWidgetHeight,
                    initColor = initialHSV[0],
                    cornerRadius = defaultCornerRadius,
                    setColor = { hue ->
                        hsv.value = Triple(hue, hsv.value.second, hsv.value.third)
                    }
                )
            }
        }

        AnimatedVisibility(hideColorPicker) {
            NeuPulsateEffectFlatButton(
                onClick = {},
                modifier = modifier,
                animation = animation,
                contentPadding = buttonContentsPadding
            ) {
                SaturationValuePanel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(colorPickerWidgetHeight),
                    hue = hsv.value.first,
                    aspectRatio = null,
                    cornerRadius = defaultCornerRadius,
                    setSatVal = { sat, value ->
                        hsv.value = Triple(hsv.value.first, sat, value)
                    }
                )
            }
        }
    }
}

@Composable
fun ScheduleTimeButton(
    title: String = "",
    schedulingEnabled: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    timeString: MutableState<String> = rememberSaveable { mutableStateOf("--:--")},
    openSelector: () -> Unit = {},
    modifier: Modifier = Modifier,
    resizer: MutableState<Float> = remember { mutableStateOf(1f) }
) {
    val Up = AnimatedContentTransitionScope.SlideDirection.Up
    val Down = AnimatedContentTransitionScope.SlideDirection.Down
    Row(modifier, Arrangement.spacedBy(4.dp), Alignment.CenterVertically) {
        BodyText(title, Modifier.padding(PaddingValues(horizontal = 4.dp, vertical = 0.dp)), resizer = resizer)
        PulsateEffectButton(
            onClick = {
                openSelector()
                schedulingEnabled.value = true
            },
            modifier = Modifier.weight(1f),
            shape = defaultCornerRoundShape,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            elevation = null
        ) {
            val (hour, minute) = timeString.value.split(":")
            AnimatedContent(
                targetState = if (schedulingEnabled.value) hour else "--",
                transitionSpec = {
                    slideIntoContainer(
                        towards = if (schedulingEnabled.value) Down else Up,
                        animationSpec = tween(durationMillis = 500)
                    ) togetherWith ExitTransition.None
                }
            ) { time ->
                StatusText(time, resizer = resizer)
            }
            StatusText(":", resizer = resizer)
            AnimatedContent(
                targetState = if (schedulingEnabled.value) minute else "--",
                transitionSpec = {
                    slideIntoContainer(
                        towards = if (schedulingEnabled.value) Up else Down,
                        animationSpec = tween(durationMillis = 500)
                    ) togetherWith ExitTransition.None
                }
            ) { time ->
                StatusText(time, resizer = resizer)
            }
        }
    }
}

@Composable
fun ReconnectionScheduleButton(
    schedulingEnabled: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    reconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("07:00")},
    disconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("32:00")},
    openReconnectTimeSelector: () -> Unit = {},
    openDisconnectTimeSelector: () -> Unit = {}
) {
    val resizer = remember { mutableStateOf(1f) }
    NeuPulsateEffectFlatButton(
        onClick = {
            openReconnectTimeSelector()
            openDisconnectTimeSelector()
            schedulingEnabled.value = true
        },
        modifier = Modifier.fillMaxWidth()
            .onSizeChanged {
                resizer.value = 1f
            },
        animation = ClickAnimation(1f, 0.985f),
        contentPadding = PaddingValues(12.dp, 8.dp, 12.dp, 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val haptic = LocalHapticFeedback.current
            val scope = rememberCoroutineScope()
            HeadText(LS.mainConnectionSchedule, Modifier.padding(PaddingValues(4.dp, 0.dp)))
            DrawableMain.ScheduleRegisterButton(onClick = {
                schedulingEnabled.value = !schedulingEnabled.value
                scope.launch {
                    textAnimationHaptic {
                        haptic.performHapticFeedback(it)
                    }
                }
            }, isOn = schedulingEnabled.value)
        }
        Spacer(Modifier.fillMaxWidth().height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .onSizeChanged {
                    resizer.value = 1f
                },
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScheduleTimeButton(
                LS.mainReconnectSchedule, schedulingEnabled, reconnectTimeString,
                openReconnectTimeSelector, Modifier.weight(1f), resizer)
            ScheduleTimeButton(
                LS.mainDisconnectSchedule, schedulingEnabled, disconnectTimeString,
                openDisconnectTimeSelector, Modifier.weight(1f), resizer)
        }
    }
}
