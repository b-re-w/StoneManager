package io.github.irack.stonemanager.`interface`.ui.style

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.*


class DialShape(private val pointerDegrees: Float, private val radius: Float) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Generic(path = Path().apply { drawDial(pointerDegrees/100*360+90, radius, size.center) })
    }
}

fun Path.drawDial(pointerDegrees: Float, radius: Float, center: Offset) {
    this.fillType = PathFillType.NonZero

    addOval(Rect(center, radius))
    close()

    val pointer = Path()
    val angle: Float = degToRad((pointerDegrees % 360 + 360) % 360.0).toFloat()

    //      A
    //     / \
    //   B/ D \C
    val angleGap = degToRad(16.02).toFloat()
    val pointerHeightRatio = 1.3 / 0.85
    val Bx = radius * cos(angle + angleGap)
    val By = radius * sin(angle + angleGap)
    val Cx = radius * cos(angle - angleGap)
    val Cy = radius * sin(angle - angleGap)
    val Dx = (Bx + Cx) / 2.0f
    val Dy = (By + Cy) / 2.0f
    val D = sqrt(Dx*Dx + Dy*Dy)  // length to D
    val pointerHeight = sqrt((Bx-Cx).pow(2) + (By-Cy).pow(2)) / 2 * pointerHeightRatio
    val A = D + pointerHeight.toFloat()  // length to A
    val Ax = A * cos(angle)
    val Ay = A * sin(angle)

    pointer.moveTo(x = center.x + Bx, y = center.y + By)
    pointer.lineTo(x = center.x + Ax, y = center.y + Ay)
    pointer.lineTo(x = center.x + Cx, y = center.y + Cy)
    pointer.close()

    this.op(this, pointer, PathOperation.Union)
}

fun degToRad(degree: Double): Double {
    return degree * PI / 180
}
