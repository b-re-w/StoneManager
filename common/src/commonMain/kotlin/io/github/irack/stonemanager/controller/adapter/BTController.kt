package io.github.irack.stonemanager.controller.adapter

import androidx.compose.runtime.MutableState
import com.abhilash.apps.composecolorpicker.hsvToArgbList


enum class LightingType(val value: Int) {
    Off(0), RGB(1), Candle(2), Aurora(3), Wave(4), Firefly(5);

    companion object {
        fun fromValue(value: Int): LightingType = when (value) {
            0 -> Off
            1 -> RGB
            2 -> Candle
            3 -> Aurora
            4 -> Wave
            5 -> Firefly
            else -> Off
        }
    }
}


fun MutableState<IntArray>.modify(
    brightness: Int? = null, lightingType: LightingType? = null, hsv: Triple<Float, Float, Float>? = null
): MutableState<IntArray> {
    var bright = this.value[0]
    if (brightness != null) {
        bright = if (brightness < 0) {
            0
        } else if (brightness > 100) {
            100
        } else {
            brightness
        }
    }

    var type = this.value[1]
    if (lightingType != null) {
        type = lightingType.value
    }

    var red = this.value[2]
    var green = this.value[3]
    var blue = this.value[4]
    if (hsv != null) {
        val argv = hsvToArgbList(hsv.first, hsv.second, hsv.third)
        red = argv[1]
        green = argv[2]
        blue = argv[3]
    }

    this.value = intArrayOf(bright, type, red, green, blue)
    return this
}
