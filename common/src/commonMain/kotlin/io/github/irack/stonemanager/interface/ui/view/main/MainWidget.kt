package io.github.irack.stonemanager.`interface`.ui.view.main

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhilash.apps.composecolorpicker.HueBar
import com.abhilash.apps.composecolorpicker.SaturationValuePanel
import com.abhilash.apps.composecolorpicker.argbToHsv
import com.gandiva.neumorphic.shape.CornerShape
import com.gandiva.neumorphic.shape.RoundedCorner
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain.LampLightHigh
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain.LampLightLow
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain.VolumeMax
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain.VolumeMute
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain.VolumeWheelBackground
import io.github.irack.stonemanager.`interface`.resource.drawable.DrawableMain.VolumeWheelForeground
import io.github.irack.stonemanager.`interface`.resource.localization.LS
import io.github.irack.stonemanager.`interface`.resource.util.convertDpToPixels
import io.github.irack.stonemanager.`interface`.resource.util.getPlatform
import io.github.irack.stonemanager.`interface`.ui.style.*
import io.github.irack.stonemanager.`interface`.ui.theme.*
import io.github.irack.stonemanager.`interface`.ui.unit.toList
import io.github.seyoungcho2.slider.FilledSlider
import io.github.seyoungcho2.slider.model.SliderColor
import io.github.seyoungcho2.slider.model.SliderOrientation
import io.github.seyoungcho2.slider.model.SliderType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun VolumeWheelDial(
    isViewLaunched: Boolean = false,
    volumeLevelState: MutableState<Int> = rememberSaveable { mutableStateOf(14) }
) {
    val showState = rememberSaveable { mutableStateOf(false) }
    val wheelSize = animateDpAsState(if (showState.value) 200.dp else 120.dp,
        animationSpec = tween(500))
    val innerWheelSize = wheelSize.value / 1.6f
    val iconOffset = animateDpAsState(if (isViewLaunched) 17.dp else 0.dp,
        animationSpec = tween(500, delayMillis = 1000))

    Row(Modifier.fillMaxWidth().padding(0.dp, 0.dp, 0.dp, 20.dp), Arrangement.Center, Alignment.Bottom) {
        AnimatedVisibility(isViewLaunched, enter = fadeInSlideInHorizontally(toLeftDirection = false)) {
            VolumeMute("", Modifier.size(34.dp).offset(x = iconOffset.value))  // TODO: fill desc
        }

        NeuPulsateEffectFlatButton({}, Modifier, false,
            colors = ButtonDefaults.buttonColors(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent),
            neuShape = RoundedCorner(wheelSize.value/2f),
            shape = RoundedCornerShape(40),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(Modifier.background(Color.Transparent), Alignment.Center) {
                VolumeWheelBackground("", Modifier.size(wheelSize.value))  // TODO: fill desc
                NeuPulsateEffectFlatButton({},
                    modifier = Modifier
                        .size(wheelSize.value/1.15f)
                        .trackCircularSlider(
                            14, 86,
                            circleCenter = Offset(convertDpToPixels(wheelSize.value)/2, convertDpToPixels(wheelSize.value)/2),
                            sensitivity = 2f,
                            positionValue = volumeLevelState
                        ),
                    shape = DialShape(volumeLevelState.value.toFloat(), convertDpToPixels(innerWheelSize)/2f),
                    neuShape = RoundedCorner(wheelSize.value/2f),
                    lightShadowColor = Color(0x13b89dc6),
                    darkShadowColor = Color(0x08000000),
                    shadowElevation = wheelSize.value/14f,
                    border = BorderStroke(1.2.dp, Brush.linearGradient(listOf(Color(0xB4F6F8FC), Color(0x20767171)))),
                    contentPadding = PaddingValues(0.dp),
                    interactionSource = remember { NoRippleInteractionSource() }
                ) {
                    VolumeWheelForeground("", Modifier.size(wheelSize.value))  // TODO: fill desc
                }
            }
        }

        AnimatedVisibility(isViewLaunched, enter = fadeInSlideInHorizontally(toLeftDirection = true)) {
            VolumeMax("", Modifier.size(34.dp).offset(x = -iconOffset.value))
        }
    }

    val scope = rememberCoroutineScope()
    scope.launch {
        if (!isViewLaunched) {
            delay(1000)
            showState.value = !isViewLaunched
        } else {
            showState.value = isViewLaunched
        }
    }
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
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    shadowElevation: Dp = defaultNeuElevation
) {
    Row(modifier, horizontalArrangement, verticalAlignment) {
        NeuPulsateEffectFlatButton(openLampTypeSelector, Modifier.weight(1f), shadowElevation = shadowElevation) {
            BodyText(LS.mainLampDescriptor)
            Spacer(Modifier.height(6.dp))
            val statusString = LS.mainLampStatus.toList()
            StatusText(
                text = statusString.getOrElse(lampType.value) { statusString[0] },
                overflow = TextOverflow.Ellipsis
            )
        }
        NeuPulsateEffectFlatButton({}, Modifier.weight(1.2f), shadowElevation = shadowElevation) {
            BodyText(LS.mainBatteryDescriptor)
            Spacer(Modifier.height(6.dp))
            StatusText("100%", overflow = TextOverflow.Ellipsis)  // TODO: Battery Status
        }
        NeuPulsateEffectFlatButton(openWelcomeLightTypeSelector, Modifier.weight(1.4f), shadowElevation = shadowElevation) {
            BodyText(LS.mainOnReconnectDescriptor)
            Spacer(Modifier.height(6.dp))
            StatusText(LS.mainOnReconnectStatus1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun LampControlSpace(
    pickerHideLevel: Int = 0,
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
    animation: ClickAnimation = ClickAnimation(1f, 0.97f),
    columnSpacedBy: Dp = 12.dp,
    shadowElevation: Dp = defaultNeuElevation
) {
    val hideBrightnessPanel = pickerHideLevel < 1
    val hideColorPicker = pickerHideLevel != 1
    val buttonContentsPadding = PaddingValues(0.dp)
    Column(modifier, Arrangement.spacedBy(columnSpacedBy), Alignment.CenterHorizontally) {
        val (state1, transition1, setter1)
            = createNeuAnimation(0, 400, true, defaultNeuElevation)
        setter1(!hideBrightnessPanel)
        AnimatedVisibility(!hideBrightnessPanel, enter = transition1.first, exit = transition1.second) {
            NeuPulsateEffectFlatButton(
                onClick = {},
                modifier = modifier,
                animation = animation,
                contentPadding = buttonContentsPadding,
                shadowElevation = state1.value.dp + shadowElevation - defaultNeuElevation
            ) {
                Box(Modifier, Alignment.Center) {
                    FilledSlider(
                        modifier = Modifier
                            .height(colorPickerWidgetHeight)
                            .fillMaxWidth(),
                        sliderShape = defaultCornerRoundShape,
                        isEnabled = true,
                        sliderColor = SliderColor(
                            enabledTrackColor = appColorSet.brightPanelBackground,
                            enabledIndicationColor = appColorSet.brightPanelForeground
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
                    Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                        LampLightLow("", Modifier.size(colorPickerWidgetHeight/1.7f))
                        LampLightHigh("", Modifier.size(colorPickerWidgetHeight/1.7f))
                    }
                }
            }
        }

        val (state2, transition2, setter2)
            = createNeuAnimation(200, 200, true, defaultNeuElevation)
        setter2(!hideColorPicker)
        AnimatedVisibility(!hideColorPicker, enter = transition2.first, exit = transition2.second) {
            NeuPulsateEffectFlatButton(
                onClick = {},
                modifier = modifier,
                animation = animation,
                contentPadding = buttonContentsPadding,
                shadowElevation = state2.value.dp + shadowElevation - defaultNeuElevation
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

        val (state3, transition3, setter3)
            = createNeuAnimation(400, 0, true, defaultNeuElevation)
        setter3(!hideColorPicker)
        AnimatedVisibility(!hideColorPicker, enter = transition3.first, exit = transition3.second) {
            NeuPulsateEffectFlatButton(
                onClick = {},
                modifier = modifier,
                animation = animation,
                contentPadding = buttonContentsPadding,
                shadowElevation = state3.value.dp + shadowElevation - defaultNeuElevation
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
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            elevation = null
        ) {
            val (hour, minute) = timeString.value.split(":")
            val onSecondary = MaterialTheme.colorScheme.onSecondary
            AnimatedContent(
                targetState = if (schedulingEnabled.value) hour else "--",
                transitionSpec = {
                    slideIntoContainer(
                        towards = if (schedulingEnabled.value) Down else Up,
                        animationSpec = tween(durationMillis = 500)
                    ) togetherWith ExitTransition.None
                }
            ) { time ->
                StatusText(time, color = onSecondary, resizer = resizer, overflow = TextOverflow.Visible)
            }
            StatusText(":", color = onSecondary, resizer = resizer)
            AnimatedContent(
                targetState = if (schedulingEnabled.value) minute else "--",
                transitionSpec = {
                    slideIntoContainer(
                        towards = if (schedulingEnabled.value) Up else Down,
                        animationSpec = tween(durationMillis = 500)
                    ) togetherWith ExitTransition.None
                }
            ) { time ->
                StatusText(time, color = onSecondary, resizer = resizer, overflow = TextOverflow.Visible)
            }
        }
    }
}

@Composable
fun ReconnectionScheduleButton(
    schedulingEnabled: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    reconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("07:00")},
    disconnectTimeString: MutableState<String> = rememberSaveable { mutableStateOf("23:00")},
    openReconnectTimeSelector: () -> Unit = {},
    openDisconnectTimeSelector: () -> Unit = {},
    shadowElevation: Dp = defaultNeuElevation
) {
    val resizer = remember { mutableStateOf(0.6f) }
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
        animation = ClickAnimation(1f, 0.97f),
        contentPadding = PaddingValues(12.dp, 8.dp, 12.dp, 12.dp),
        shadowElevation = shadowElevation
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
