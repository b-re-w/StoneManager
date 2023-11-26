package io.github.irack.stonemanager.util


actual fun getCurrentLanguage(): String {
    return platform.Locale.preferredLanguages[0]
}
