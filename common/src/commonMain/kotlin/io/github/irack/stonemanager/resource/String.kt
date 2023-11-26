package io.github.irack.stonemanager.resource

import io.github.irack.stonemanager.util.Locale

abstract class LocalizedString {
    abstract val locale: String
    abstract val appName: String
}

val supportedLocalizations = mapOf(
    EnUsString.locale to EnUsString,
    KoKrString.locale to KoKrString,
    "default" to EnUsString
)

val supportedLanguages = supportedLocalizations.map { it.key.split("-")[0] to it.value }.reversed().toMap()

val LString: LocalizedString
    get() {
        val locale =  Locale.getCurrentLocale()
        return if (supportedLocalizations.contains(locale)) {
            supportedLocalizations[locale] as LocalizedString
        } else if (locale.split("-")[0] in supportedLanguages.keys) {
            supportedLanguages[locale.split("-")[0]] as LocalizedString
        } else {  // Default locale
            supportedLocalizations["default"] as LocalizedString
        }
    }
