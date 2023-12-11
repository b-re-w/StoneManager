package io.github.irack.stonemanager.`interface`.resource.util

import platform.Foundation.*


actual fun isAfter6AMBefore6PM(): Boolean {
    val currentHour = NSCalendar.currentCalendar.components(
        NSCalendarUnitHour,
        NSDate().dateByAddingTimeInterval(0.0),
        null
    ).hour

    return currentHour >= 6 && currentHour < 18
}
