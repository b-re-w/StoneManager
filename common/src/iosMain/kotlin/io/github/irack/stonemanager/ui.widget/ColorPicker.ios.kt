@file:Suppress("NAME_SHADOWING")

package io.github.irack.stonemanager.ui.widget

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import org.jetbrains.skia.Rect


actual fun DrawScope.initBitmapCanvasPanel(): Triple<Any, Any, Pair<Any, Int>> {
    val bitmap = Bitmap()
    bitmap.allocN32Pixels(size.width.toInt(), size.height.toInt(), false)
    val imageBitmap = bitmap.asComposeImageBitmap()
    val hueCanvas = Canvas(image = imageBitmap)
    val huePanel = Rect(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
    return Triple(imageBitmap, hueCanvas, Pair(huePanel, (huePanel.width).toInt()))
}

actual fun drawColorLines(hueCanvas: Any, huePanel: Any, hueColors: IntArray) {
    val hueCanvas = hueCanvas as Canvas
    val huePanel = huePanel as Rect
    val linePaint = Paint()
    linePaint.strokeWidth = 0F
    for (i in hueColors.indices) {
        linePaint.color = Color(hueColors[i])
        hueCanvas.drawLine(Offset(i.toFloat(), 0F), Offset(i.toFloat(), huePanel.bottom), paint = linePaint)
    }
}


actual fun DrawScope.drawBitmap(bitmap: Any, panel: Any) {
    val bitmap = bitmap as ImageBitmap
    val panel = panel as Rect
    drawIntoCanvas {
        it.nativeCanvas.drawImageRect(Image.makeFromBitmap(bitmap.asSkiaBitmap()), panel)
    }
}

actual fun getHuePositioningFuntion(huePanel: Any): (Float) -> Float {
    val huePanel = huePanel as Rect
    val pointToHue: (pointX: Float) -> Float = { pointX ->
        val width = huePanel.width
        val x = when {
            pointX < huePanel.left -> 0F
            pointX > huePanel.right -> width
            else -> pointX - huePanel.left
        }
        (x * 360f) / width
    }
    return pointToHue
}
