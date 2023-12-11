package io.github.irack.stonemanager.`interface`.resource.util

import androidx.core.os.LocaleListCompat


internal actual fun getCurrentLanguage(): String {
    return LocaleListCompat.getAdjustedDefault().toLanguageTags().split(",")[0]
}
