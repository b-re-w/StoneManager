package io.github.irack.stonemanager.ui.style

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.CornerShape
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.Pressed
import io.github.irack.stonemanager.ui.theme.appColorSet
import io.github.irack.stonemanager.ui.theme.defaultCornerNeuShape
import io.github.irack.stonemanager.ui.theme.defaultCornerRoundShape
import io.github.irack.stonemanager.ui.theme.defaultNeuElevation


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
