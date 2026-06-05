package com.truongnt.fsd.fsd_base_and.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Horizontal hue slider (0..360°).
 * Draws a rainbow gradient and a draggable thumb.
 */
class HueSliderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var hue: Float = 275f
        set(value) {
            field = value.coerceIn(0f, 360f)
            invalidate()
        }

    var onHueChanged: ((Float) -> Unit)? = null

    private val hueColors = intArrayOf(
        Color.RED,
        Color.YELLOW,
        Color.GREEN,
        Color.CYAN,
        Color.BLUE,
        Color.MAGENTA,
        Color.RED
    )

    private val rainbowPaint = Paint()
    private val trackRect = RectF()
    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }
    private val thumbStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = Color.parseColor("#33000000")
    }

    private val trackHeight = 40f
    private val thumbRadius = 26f
    private val cornerRadius = 20f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cy = h / 2f
        trackRect.set(0f, cy - trackHeight / 2f, w.toFloat(), cy + trackHeight / 2f)
        rainbowPaint.shader = LinearGradient(
            0f, 0f, w.toFloat(), 0f,
            hueColors, null, Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw rainbow track
        canvas.drawRoundRect(trackRect, cornerRadius, cornerRadius, rainbowPaint)

        // Draw thumb
        val tx = (hue / 360f) * width
        val cy = height / 2f
        // Shadow
        val shadowPaint = Paint(thumbPaint).apply {
            color = Color.parseColor("#44000000")
        }
        canvas.drawCircle(tx, cy, thumbRadius + 2f, shadowPaint)
        canvas.drawCircle(tx, cy, thumbRadius, thumbPaint)
        canvas.drawCircle(tx, cy, thumbRadius, thumbStrokePaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                hue = ((event.x / width) * 360f).coerceIn(0f, 360f)
                onHueChanged?.invoke(hue)
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}
