package io.github.irack.stonemanager.util

import java.util.Calendar


actual fun isAfter6AMBefore6PM(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return currentHour in 6..17
}
