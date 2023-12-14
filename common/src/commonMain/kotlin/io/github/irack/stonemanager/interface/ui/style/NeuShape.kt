package io.github.irack.stonemanager.`interface`.ui.style

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.CornerShape
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.Pressed
import io.github.irack.stonemanager.`interface`.ui.theme.appColorSet
import io.github.irack.stonemanager.`interface`.ui.theme.defaultCornerNeuShape
import io.github.irack.stonemanager.`interface`.ui.theme.defaultCornerRoundShape
import io.github.irack.stonemanager.`interface`.ui.theme.defaultNeuElevation
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.sqrt


@Composable
fun NeuEffectButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = defaultCornerRoundShape,
    neuShape: CornerShape = defaultCornerNeuShape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(4.dp, 8.dp),
    content: @Composable ColumnScope.() -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()

    Button(
        onClick = onClick,
        modifier = modifier
            .neu(
                lightShadowColor = appColorSet.lightShadow,
                darkShadowColor = appColorSet.darkShadow,
                shadowElevation = defaultNeuElevation,
                lightSource = LightSource.LEFT_TOP,
                shape = if (isPressed.value) Pressed(neuShape) else Flat(neuShape)
            ),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}


@Composable
fun NeuPulsateEffectFlatButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    animation: ClickAnimation = ClickAnimation(1f, 0.94f),
    shape: Shape = defaultCornerRoundShape,
    neuShape: CornerShape = defaultCornerNeuShape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    shadowElevation: Dp = defaultNeuElevation,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(8.dp, 12.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current
    PulsateEffectButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = modifier
            .neu(
                lightShadowColor = appColorSet.lightShadow,
                darkShadowColor = appColorSet.darkShadow,
                shadowElevation = shadowElevation,
                lightSource = LightSource.LEFT_TOP,
                shape = Flat(neuShape)
            ),
        enabled = enabled,
        animation = animation,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
fun rememberForeverNeuAnimation(
    enterDelay: Int, exitDelay: Int, duration: Int, initialStateVisibility: Boolean = false, maxElevation: Dp = defaultNeuElevation
): Pair<MutableState<Float>, ((Boolean) -> Unit)> {
    val animationState: MutableState<Boolean> = rememberSaveable { mutableStateOf(initialStateVisibility) }
    val currentElevation: MutableState<Float> = rememberSaveable { mutableStateOf(if (initialStateVisibility) maxElevation.value else 0f) }
    val increment = maxElevation.value / (duration*2f)
    val decrement = maxElevation.value / duration

    LaunchedEffect(animationState.value) {
        if (!animationState.value) {
            delay(exitDelay.toLong())
            var currentMilli = 0.0
            while (currentMilli < duration) {
                currentElevation.value -= decrement
                delay(duration * easeOutExpo(currentMilli++/duration).toLong())
            }
            currentElevation.value = 0f
        } else {
            delay((enterDelay/1.2).toLong())
            var currentMilli = duration.toDouble()
            while (currentMilli > 0) {
                currentElevation.value += increment
                delay(duration * easeInCirc(currentMilli--/duration).toLong())
            }
            currentElevation.value = maxElevation.value
        }
    }
    return Pair(currentElevation) { isToReveal -> animationState.value = isToReveal }
}

fun easeInExpo(x: Double): Double {
    return if (x == 0.0) 0.0 else 2.0.pow(10 * x - 10)
}

fun easeOutExpo(x: Double): Double {
    return if (x == 1.0) {
        1.0
    } else {
        1 - 2.0.pow(-10 * x)
    }
}

fun easeInCirc(x: Double): Double {
    return 1 - sqrt(1 - x.pow(2))
}

fun easeOutCirc(x: Double): Double {
    return sqrt(1 - (x - 1).pow(2))
}


@Composable
fun createNeuAnimation(  // TODO: refactor to use animateFloatAsState
    enterDelay: Int, exitDelay: Int, initialStateVisibility: Boolean = false, maxElevation: Dp = defaultNeuElevation
): Triple<MutableState<Float>, Pair<EnterTransition, ExitTransition>, ((Boolean) -> Unit)> {
    val (state, trigger) = rememberForeverNeuAnimation(enterDelay, exitDelay, 400, initialStateVisibility, maxElevation)
    val enterAnimation: EnterTransition = fadeIn(
        animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing, delayMillis = enterDelay)
    ) + slideInVertically(
        initialOffsetY = { fullHeight -> fullHeight },
        animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing, delayMillis = enterDelay)
    )
    val exitAnimation: ExitTransition = fadeOut(
        animationSpec = tween(durationMillis = 400, easing = FastOutLinearInEasing, delayMillis = exitDelay)
    ) + slideOutVertically(
        targetOffsetY = { fullHeight -> fullHeight },
        animationSpec = tween(durationMillis = 400, easing = FastOutLinearInEasing, delayMillis = exitDelay)
    )
    return Triple(state, Pair(enterAnimation, exitAnimation), trigger)
}
