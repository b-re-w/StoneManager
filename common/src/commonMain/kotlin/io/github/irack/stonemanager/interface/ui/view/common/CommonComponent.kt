package io.github.irack.stonemanager.`interface`.ui.view.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.irack.stonemanager.`interface`.resource.localization.LS
import io.github.irack.stonemanager.`interface`.ui.theme.BodyText
import io.github.irack.stonemanager.`interface`.ui.theme.FootText


@Composable
fun Header(paddingValues: PaddingValues = PaddingValues(0.dp, 6.dp, 0.dp, 26.dp)) {
    BodyText(LS.appViewHeader, Modifier.padding(paddingValues))
}

@Composable
fun Footer(paddingValues: PaddingValues = PaddingValues(0.dp, 26.dp, 0.dp, 6.dp)) {
    FootText(LS.appViewFooter, Modifier.padding(paddingValues))
}
