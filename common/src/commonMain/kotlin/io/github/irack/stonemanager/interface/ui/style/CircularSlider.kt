package io.github.irack.stonemanager.`interface`.ui.style

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.*


@Composable
fun CustomCircularProgressIndicator(
    circleRadius: Float,
    modifier: Modifier = Modifier,
    initialValue: Int = 50,
    primaryColor: Color = Color(0xFFdb660d),
    minValue: Int = 0,
    maxValue: Int = 100,
    valueRange: Int = 100,
    onPositionChange: (Int) -> Unit = {}
) {
    val circleCenter = remember { mutableStateOf(Offset.Zero) }
    val positionValue = rememberSaveable { mutableStateOf(initialValue) }

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .trackCircularSlider(
                    minValue, maxValue, valueRange, circleCenter.value,
                    positionValue = positionValue, onPositionChange = onPositionChange
                )
        ){
            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            circleCenter.value = Offset(x = width/2f, y = height/2f)

            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f/maxValue) * positionValue.value.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f)/2f,
                    (height - circleRadius * 2f)/2f
                )
            )
        }
    }
}

@Composable
fun Modifier.trackCircularSlider(
    minValue: Int = 0,
    maxValue: Int = 100,
    valueRange: Int = 100,
    circleCenter: Offset = Offset.Zero,
    sensitivity: Float = 1f,
    positionValue: MutableState<Int> = rememberSaveable { mutableStateOf(50) },
    changeAngle: MutableState<Float> = rememberSaveable { mutableStateOf(0f) },
    dragStartedAngle: MutableState<Float> = rememberSaveable { mutableStateOf(0f) },
    oldPositionValue: MutableState<Int> = rememberSaveable { mutableStateOf(50) },
    onPositionChange: (Int) -> Unit = {}
): Modifier = this.pointerInput(true) {
    detectDragGestures(
        onDragStart = { offset ->
            dragStartedAngle.value = -atan2(
                x = circleCenter.y - offset.y,
                y = circleCenter.x - offset.x
            ) * (180f / PI).toFloat()
            dragStartedAngle.value = (dragStartedAngle.value + 180f).mod(360f)
        },
        onDrag = { change, _ ->
            var touchAngle = -atan2(
                x = circleCenter.y - change.position.y,
                y = circleCenter.x - change.position.x
            ) * (180f / PI).toFloat()
            touchAngle = (touchAngle + 180f).mod(360f)

            val currentAngle = oldPositionValue.value*360f/valueRange
            changeAngle.value = touchAngle - currentAngle

            val lowerThreshold = currentAngle - (360f/valueRange * 10 * sensitivity)
            val higherThreshold = currentAngle + (360f/valueRange * 10 * sensitivity)

            if (dragStartedAngle.value in lowerThreshold .. higherThreshold) {
                val position = (oldPositionValue.value + (changeAngle.value/(360f/valueRange)).roundToInt())
                val compareTarget = if (oldPositionValue.value != positionValue.value) {  // keep touching without drag-end
                    positionValue.value
                } else {  // not continuous drag
                    oldPositionValue.value
                }
                if (abs(compareTarget - position) <= (maxValue-minValue)/1.5) {  // block value flipping to opposite value pole
                    positionValue.value = when {
                        position > maxValue -> maxValue
                        position < minValue -> minValue
                        else -> position
                    }
                }
            }
        },
        onDragEnd = {
            oldPositionValue.value = positionValue.value
            onPositionChange(positionValue.value)
        }
    )
}
