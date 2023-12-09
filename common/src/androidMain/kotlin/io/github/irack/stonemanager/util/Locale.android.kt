package io.github.irack.stonemanager.util

import androidx.core.os.LocaleListCompat


internal actual fun getCurrentLanguage(): String {
    return LocaleListCompat.getAdjustedDefault().toLanguageTags().split(",")[0]
}
