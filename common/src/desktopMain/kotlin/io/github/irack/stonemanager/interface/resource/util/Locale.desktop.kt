package io.github.irack.stonemanager.`interface`.resource.util


internal actual fun getCurrentLanguage(): String {
    return System.getProperty("user.language") + "-" + System.getProperty("user.country")
}
