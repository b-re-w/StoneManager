package io.github.irack.stonemanager.util

import platform.Foundation.NSDate
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSTimeZone


actual fun isAfter6AMBefore6PM(): Boolean {
    val currentHour = NSCalendar.currentCalendar.components(
        NSCalendarUnitHour,
        NSDate().dateByAddingTimeInterval(0.0),
        null
    ).hour

    return currentHour >= 6 && currentHour < 18
}
