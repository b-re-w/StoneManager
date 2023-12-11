package io.github.irack.stonemanager.`interface`.resource.util


data class LocaleInfo(var language: String, var country: String) {
    init {
        language = language.lowercase()
        country = country.uppercase()
    }
    override fun toString(): String {
        return "${language}-${country}"
    }

    val full: String
        get() = this.toString()

    companion object {
        fun parseFrom(locale: String): LocaleInfo {
            val (lang, coun) = locale.split("-")
            return LocaleInfo(lang, coun)
        }
    }
}

object Locale {

    fun getCurrentLocaleInfo(): LocaleInfo = LocaleInfo.parseFrom(getCurrentLanguage())

    fun getCurrentLocale(): String = getCurrentLocaleInfo().toString()
}

internal expect fun getCurrentLanguage(): String
