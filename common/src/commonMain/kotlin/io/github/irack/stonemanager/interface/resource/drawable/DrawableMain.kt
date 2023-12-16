package io.github.irack.stonemanager.`interface`.resource.drawable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import io.github.irack.stonemanager.`interface`.resource.localization.LS
import io.github.irack.stonemanager.`interface`.ui.style.ClickAnimation
import io.github.irack.stonemanager.`interface`.ui.style.bounceClick
import io.github.irack.stonemanager.`interface`.ui.theme.appColorSet
import io.github.irack.stonemanager.`interface`.ui.theme.defaultCornerRoundShape
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


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

    @Composable
    fun LampLightHigh(desc: String, modifier: Modifier = Modifier, tint: Color = appColorSet.brightPanelForeground) {
        Drawable("drawable/main/backlight_high_fill0_wght200_grad_25_opsz20.xml", desc, modifier, tint)
    }

    @Composable
    fun LampLightLow(desc: String, modifier: Modifier = Modifier, tint: Color = appColorSet.brightPanelBackground) {
        Drawable("drawable/main/backlight_low_fill0_wght200_grad_25_opsz20.xml", desc, modifier, tint)
    }

    @Composable
    fun VolumeMax(desc: String, modifier: Modifier = Modifier, tint: Color = MaterialTheme.colorScheme.onBackground) {
        Drawable("drawable/main/volume_up_fill1_wght200_grad0_opsz24.xml", desc, modifier, tint)
    }

    @Composable
    fun VolumeMute(desc: String, modifier: Modifier = Modifier, tint: Color = MaterialTheme.colorScheme.onBackground) {
        Drawable("drawable/main/volume_mute_fill1_wght200_grad0_opsz24.xml", desc, modifier, tint)
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun VolumeWheelBackground(desc: String, modifier: Modifier = Modifier) {
        Image(painterResource("drawable/main/volume_wheel_background.xml"), desc, modifier.clip(RoundedCornerShape(50)))
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun VolumeWheelForeground(desc: String, modifier: Modifier = Modifier) {
        Image(painterResource("drawable/main/volume_wheel_foreground.xml"), desc, modifier.clip(RoundedCornerShape(50)))
    }

    object SelectDialog {
        private const val PATH = "drawable/main/selectDialog/"

        @Composable
        fun Lamp(desc: String, modifier: Modifier = Modifier, tint: Color = MaterialTheme.colorScheme.onPrimary) {
            Drawable("${PATH}backlight_high_fill1_wght700_grad200_opsz20.xml", desc, modifier, tint)
        }

        @Composable
        fun GradientIcon(res: String, desc: String, modifier: Modifier = Modifier, brushGradient: Brush) {
            Drawable(
                res = res,
                modifier = modifier
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(brushGradient, blendMode = BlendMode.SrcAtop)
                        }
                    },
                contentDescription = desc,
            )
        }

        @Composable
        fun Off(desc: String, modifier: Modifier) {
            GradientIcon("${PATH}disabled_by_default_fill1_wght700_grad200_opsz20.xml", desc, modifier, Brush.linearGradient(
                listOf(Color.White, MaterialTheme.colorScheme.onBackground, Color.Gray),
                start = Offset(0.0f, 0.0f),
                end = Offset(100.0f, 100.0f)
            ))
        }

        @Composable
        fun RGB(desc: String, modifier: Modifier) {
            GradientIcon("${PATH}palette_fill1_wght700_grad200_opsz20.xml", desc, modifier, Brush.linearGradient(
                listOf(Color.White, Color(0xfff15c58), Color(0xFFEFEB60), Color(0xff4bccae), Color(0xFF608EF7), Color(0xff8e47d5)),
                start = Offset(0.0f, 0.0f),
                end = Offset(100.0f, 100.0f)
            ))
        }

        @Composable
        fun Candle(desc: String, modifier: Modifier) {
            GradientIcon("${PATH}candle_fill1_wght700_grad200_opsz20.xml", desc, modifier, Brush.linearGradient(
                listOf(Color.White, Color.Yellow, Color.Red, Color(0xFFF86966)),
                start = Offset(0.0f, 0.0f),
                end = Offset(100.0f, 100.0f)
            ))
        }

        @Composable
        fun Aurora(desc: String, modifier: Modifier) {
            GradientIcon("${PATH}data_thresholding_fill1_wght700_grad200_opsz20.xml", desc, modifier, Brush.linearGradient(
                listOf(Color.Magenta, Color(0xFF0BADDF)),
                start = Offset(0.0f, 0.0f),
                end = Offset(100.0f, 100.0f)
            ))
        }

        @Composable
        fun Wave(desc: String, modifier: Modifier) {
            GradientIcon("${PATH}airwave_fill1_wght700_grad200_opsz20.xml", desc, modifier, Brush.linearGradient(
                listOf(Color.White, Color.Blue, Color.Cyan),
                start = Offset(0.0f, 0.0f),
                end = Offset(100.0f, 100.0f)
            ))
        }

        @Composable
        fun Firefly(desc: String, modifier: Modifier) {
            GradientIcon("${PATH}pest_control_fill1_wght700_grad200_opsz20.xml", desc, modifier, Brush.linearGradient(
                listOf(Color.Yellow, Color(0xffeae534), Color(0xff42cc6d)),
                start = Offset(0.0f, 0.0f),
                end = Offset(100.0f, 100.0f)
            ))
        }

        val lampTypeIconList: List<@Composable (String, Modifier) -> Unit>
            @Composable get() = listOf(
                { it, it1 -> Off(it, it1) },
                { it, it1 -> RGB(it, it1) },
                { it, it1 -> Candle(it, it1) },
                { it, it1 -> Aurora(it, it1) },
                { it, it1 -> Wave(it, it1) },
                { it, it1 -> Firefly(it, it1) }
            )
    }
}
