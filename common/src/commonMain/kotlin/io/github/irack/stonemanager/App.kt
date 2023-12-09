package io.github.irack.stonemanager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import io.github.irack.stonemanager.ui.theme.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.abhilash.apps.composecolorpicker.HueBar
import com.abhilash.apps.composecolorpicker.SaturationValuePanel
import com.abhilash.apps.composecolorpicker.argbToHsv
import com.abhilash.apps.composecolorpicker.rememberForeverScrollState
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import io.github.irack.stonemanager.resource.localization.LS
import io.github.irack.stonemanager.ui.style.*
import io.github.irack.stonemanager.ui.theme.*
import io.github.irack.stonemanager.unit.toList
import io.github.irack.stonemanager.util.getPlatform
import io.github.seyoungcho2.slider.FilledSlider
import io.github.seyoungcho2.slider.model.SliderColor
import io.github.seyoungcho2.slider.model.SliderOrientation
import io.github.seyoungcho2.slider.model.SliderType

@Composable
fun App(
    initialHSV: FloatArray = argbToHsv(Color.Cyan.toArgb()),
    hsv: MutableState<Triple<Float, Float, Float>> = rememberSaveable {
        mutableStateOf(
            Triple(initialHSV[0], initialHSV[1], initialHSV[2])
        )
    },
    deviceName: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    },
    lampType: MutableState<Int> = rememberSaveable {
        mutableStateOf(0)
    },
    brightness: MutableState<Float> = rememberSaveable {
        mutableStateOf(0.5f)
    }
) {
    AppTheme {
        val colorScheme = MaterialTheme.colorScheme
        val scrollState = rememberForeverScrollState("MainAppView")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp).systemBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BodyText(LS.mainHeader)

            Spacer(modifier = Modifier.height(100.dp))

            BodyText(LS.mainConnectedDevice)
            StatusText(if (deviceName.value == "") LS.mainDeviceConnectionStatus else deviceName.value)

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NeuPulsateEffectFlatButton({}) {
                    BodyText(LS.mainLampDescriptor)
                    val statusString = LS.mainLampStatus.toList()
                    StatusText(text = statusString.getOrElse(lampType.value) { statusString[0] })
                }
                Spacer(modifier = Modifier.height(10.dp))
                NeuPulsateEffectFlatButton({}) {
                    BodyText(LS.mainBatteryDescriptor)
                    StatusText("100%")
                }
                Spacer(modifier = Modifier.height(10.dp))
                NeuPulsateEffectFlatButton({}) {
                    BodyText(LS.mainOnReconnectDescriptor)
                    StatusText(LS.mainOnReconnectStatus1)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            FilledSlider(
                modifier = Modifier
                    .height(colorPickerWidgetHeight)
                    .fillMaxWidth()
                    .neu(
                        lightShadowColor = appColorSet.lightShadow,
                        darkShadowColor = appColorSet.darkShadow,
                        shadowElevation = defaultNeuElevation,
                        lightSource = LightSource.LEFT_TOP,
                        shape = Flat(defaultCornerNeuShape)
                    ),
                sliderShape = defaultCornerRoundShape,
                isEnabled = true,
                sliderColor = SliderColor(
                    enabledTrackColor = Color(0xff444444),
                    enabledIndicationColor = Color(0xffffffff)
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

            Spacer(modifier = Modifier.height(12.dp))

            HueBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .neu(
                        lightShadowColor = appColorSet.lightShadow,
                        darkShadowColor = appColorSet.darkShadow,
                        shadowElevation = defaultNeuElevation,
                        lightSource = LightSource.LEFT_TOP,
                        shape = Flat(defaultCornerNeuShape)
                    ),
                height = colorPickerWidgetHeight,
                initColor = initialHSV[0],
                cornerRadius = defaultCornerRadius,
                setColor = { hue ->
                    hsv.value = Triple(hue, hsv.value.second, hsv.value.third)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            SaturationValuePanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(colorPickerWidgetHeight)
                    .neu(
                        lightShadowColor = appColorSet.lightShadow,
                        darkShadowColor = appColorSet.darkShadow,
                        shadowElevation = defaultNeuElevation,
                        lightSource = LightSource.LEFT_TOP,
                        shape = Flat(defaultCornerNeuShape)
                    ),
                hue = hsv.value.first,
                aspectRatio = null,
                cornerRadius = defaultCornerRadius,
                setSatVal = { sat, value ->
                    hsv.value = Triple(hsv.value.first, sat, value)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            NeuPulsateEffectFlatButton(
                onClick = {

                },
                modifier = Modifier.fillMaxWidth(),
                animation = ClickAnimation(1f, 0.985f)
            ) {
                HeadText(LS.mainConnectionSchedule, fontSize = headTextFontSize)
                Row() {
                    BodyText(LS.mainReconnectSchedule)
                    BodyText(LS.mainDisconnectSchedule)
                }
            }
        }
    }
}
