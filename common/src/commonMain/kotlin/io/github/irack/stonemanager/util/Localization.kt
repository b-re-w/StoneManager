package io.github.irack.stonemanager.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.lighthousegames.logging.logging


typealias LString = LocalizedString

abstract class LocalizedString(localeString: String, prior: Boolean = true) {
    companion object {
        private val log = logging()

        var DEFAULT: LocalizedString
            get() = defaultLocalizedString
            set(value) {
                if (::defaultLocalizedString.isInitialized) {
                    if (defaultLocalizedString != value) {
                        log.w { "WARNING: In LocalizedString - LocalizedString.DEFAULT was requested to be initialized more than once. The last request was ignored, but please check where your code was written incorrectly." }
                    }
                } else {
                    defaultLocalizedString = value
                    supportedLanguagesMap["default"] = value
                }
            }

        private lateinit var defaultLocalizedString: LocalizedString

        private val supportedLocalizationsMap: MutableMap<LocaleInfo, LocalizedString> = mutableMapOf()

        private val supportedLanguagesMap: MutableMap<String, LocalizedString> = mutableMapOf()

        val supportedLocalizations: Map<LocaleInfo, LocalizedString>
            get() = supportedLocalizationsMap.toMap()

        val supportedLanguages: Map<String, LocalizedString>
            get() = supportedLanguagesMap.toMap()

        @Composable
        fun getCurrentLocalizedString(DEFAULT: LocalizedString): LocalizedString {
            LString.DEFAULT = DEFAULT
            return rememberCurrentLocalizedString()
        }
    }

    val locale: LocaleInfo
    init {
        this.locale = LocaleInfo.parseFrom(localeString)

        // Register new locale
        if (supportedLocalizationsMap.containsKey(locale)) {
            throw IllegalStateException("LocalizedString - The localization string for the locale of '$locale' already registered / duplicated. Please find the part where you created the class with the same locale string to resolve the error.")
        }
        supportedLocalizationsMap[locale] = this

        // Register new language
        if (prior) {
            if (supportedLanguagesMap.containsKey(locale.language)) {
                throw IllegalStateException("LocalizedString - The localization string for the language '${locale.language}' already registered / duplicated. When inheriting the LocalizedString class for the same language, please set the prior variable to true for only one country.")
            }
            supportedLanguagesMap[locale.language] = this
        }
    }

    private lateinit var priorAppString: LocalizedString

    protected open val PRIOR: LocalizedString
        get() = run {
            if (!this::priorAppString.isInitialized) {
                this.priorAppString = supportedLanguagesMap.getOrElse(this.locale.language) { supportedLanguages["default"] as LocalizedString }
                if (this.priorAppString == this) {  // when prior is true
                    this.priorAppString = DEFAULT
                }
            }
            return this.priorAppString
        }
}

@Composable
inline fun <reified T: LocalizedString> rememberCurrentLocalizedString(
    rememberLocale: MutableState<String> = remember { mutableStateOf("") }
): T {
    val locale =  Locale.getCurrentLocale()
    rememberLocale.value = locale
    return selectLocalizedString(LocaleInfo.parseFrom(rememberLocale.value)) as T
}

inline fun <reified T: LocalizedString> getCurrentLocalizedString(): T = selectLocalizedString(Locale.getCurrentLocaleInfo()) as T

inline fun <reified T: LocalizedString> selectLocalizedString(locale: LocaleInfo): T = LocalizedString.supportedLocalizations.getOrElse(locale) {
    LocalizedString.supportedLanguages.getOrElse(locale.language) { LocalizedString.supportedLanguages["default"] as LocalizedString }
} as T
