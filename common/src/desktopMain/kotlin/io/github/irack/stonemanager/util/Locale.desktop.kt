package io.github.irack.stonemanager.util


actual fun getCurrentLanguage(): String {
    return System.getProperty("user.language") + "-" + System.getProperty("user.country")
}
