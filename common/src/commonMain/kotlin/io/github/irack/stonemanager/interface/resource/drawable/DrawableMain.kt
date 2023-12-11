package io.github.irack.stonemanager.`interface`.resource.drawable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.irack.stonemanager.`interface`.resource.localization.LS
import io.github.irack.stonemanager.`interface`.ui.style.ClickAnimation
import io.github.irack.stonemanager.`interface`.ui.style.bounceClick
import io.github.irack.stonemanager.`interface`.ui.theme.defaultCornerRoundShape


object DrawableMain {
    @Composable
    fun ScheduleOnIcon(desc: String, modifier: Modifier = Modifier, tint: Color = MaterialTheme.colorScheme.onPrimary) {
        Drawable("drawable/main/alarm_fill0_wght200_grad_25_opsz20.xml", desc, modifier, tint)
    }

    @Composable
    fun ScheduleOffIcon(desc: String, modifier: Modifier = Modifier, tint: Color = MaterialTheme.colorScheme.onPrimary) {
        Drawable("drawable/main/alarm_off_fill0_wght200_grad_25_opsz20.xml", desc, modifier, tint)
    }

    @Composable
    fun ScheduleRegisterButton(
        onClick: () -> Unit = {},
        modifier: Modifier = Modifier
            .size(42.dp).clip(defaultCornerRoundShape)
            .bounceClick(ClickAnimation(1f, 0.8f), indication = rememberRipple(), onClick = onClick),
        isOn: Boolean = true
    ) {
        Box {
            AnimatedVisibility(isOn, enter = fadeIn(), exit = fadeOut()) {
                ScheduleOnIcon(LS.mainConnectionScheduleDeregister, modifier)
            }
            AnimatedVisibility(!isOn, enter = fadeIn(), exit = fadeOut()) {
                ScheduleOffIcon(LS.mainConnectionScheduleRegister, modifier)
            }
        }
    }
}
