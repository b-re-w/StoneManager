package io.github.irack.stonemanager.util

object Locale {
    fun getCurrentLocale() = getCurrentLanguage()
}

expect fun getCurrentLanguage(): String
