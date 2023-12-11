package io.github.irack.stonemanager.`interface`.resource.util

import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages


internal actual fun getCurrentLanguage(): String {
    return NSLocale.preferredLanguages[0] as String
}
