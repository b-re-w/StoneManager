package io.github.irack.stonemanager.util

import androidx.core.os.LocaleListCompat


actual fun getCurrentLanguage(): String {
    return LocaleListCompat.getAdjustedDefault().toLanguageTags()
}
