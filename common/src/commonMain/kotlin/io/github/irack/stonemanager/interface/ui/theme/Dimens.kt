package io.github.irack.stonemanager.`interface`.ui.theme

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gandiva.neumorphic.shape.CornerShape
import com.gandiva.neumorphic.shape.RoundedCorner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay


val defaultNeuElevation: Dp
    @Composable get() = when (AppColorSet.isLight) {
        true -> 18.dp
        false -> 14.dp
    }
val defaultCornerRadius: Dp = 36.dp
val defaultCornerRoundShape: RoundedCornerShape = RoundedCornerShape(defaultCornerRadius)
val defaultCornerNeuShape: CornerShape = RoundedCorner(defaultCornerRadius)

val colorPickerWidgetHeight: Dp = 90.dp

val bodyTextFontSize: TextUnit = 16.sp
val headTextFontSize: TextUnit = 22.sp
val footTextFontSize: TextUnit = 12.sp
val statusTextFontSize: TextUnit = 26.sp

val horizontalLayoutThreshold = 600.dp
@Composable
fun BoxWithConstraintsScope.isHorizontal(): Boolean = this.maxWidth > horizontalLayoutThreshold

val dialogOpeningHaptic: suspend CoroutineScope.((HapticFeedbackType) -> Unit) -> Unit = { performHapticFeedback ->
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(400)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(100)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(50)
    performHapticFeedback(HapticFeedbackType.LongPress)
}

val textAnimationHaptic: suspend CoroutineScope.((HapticFeedbackType) -> Unit) -> Unit = { performHapticFeedback ->
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(200)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
}
