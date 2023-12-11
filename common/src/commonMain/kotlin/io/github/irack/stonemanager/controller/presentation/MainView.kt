package io.github.irack.stonemanager.controller.presentation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.abhilash.apps.composecolorpicker.argbToHsv
import io.github.irack.stonemanager.controller.adapter.LightingType
import io.github.irack.stonemanager.controller.adapter.modify
import io.github.irack.stonemanager.ui.view.main.MainLayout


@Composable
fun MainView() {
    val initialHSV = argbToHsv(Color.Cyan.toArgb())
    val initialSettingValues = mutableStateOf(
        intArrayOf(80, 1, initialHSV[0].toInt(), initialHSV[1].toInt(), initialHSV[2].toInt())
    )

    val hsv = rememberSaveable {
        mutableStateOf(
            Triple(initialSettingValues.value[2].toFloat(),
                initialSettingValues.value[3].toFloat(), initialSettingValues.value[4].toFloat())
        )
    }
    val deviceName = rememberSaveable {
        mutableStateOf("")
    }
    val lampType = rememberSaveable {
        mutableStateOf(initialSettingValues.value[1])
    }
    val brightness = rememberSaveable {
        mutableStateOf(initialSettingValues.value[0]/100f)
    }
    val brightnessInt = derivedStateOf { (brightness.value*100).toInt() }
    val settingValues = derivedStateOf {
        (initialSettingValues.modify(brightnessInt.value, LightingType.fromValue(lampType.value), hsv.value)).value
    }
    LaunchedEffect(settingValues.value) {
        //daemon.applyChanges(settingValues.value)
    }

    MainLayout(initialHSV, hsv, deviceName, brightness, lampType)
}