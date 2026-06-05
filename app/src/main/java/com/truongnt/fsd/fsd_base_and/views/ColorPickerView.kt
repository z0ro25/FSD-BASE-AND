package com.ezt.ai.story.maker.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Custom color gradient picker view.
 * Renders a saturation/value gradient for a given hue,
 * and lets the user drag a thumb to pick a color.
 */
class ColorPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var hue: Float = 275f
        set(value) {
            field = value.coerceIn(0f, 360f)
            buildGradient()   // rebuild lớp 1 với hue mới
            invalidate()      // redraw
            // Fire callback để swatch + hex input cập nhật
            onColorChanged?.invoke(getColorAt(thumbX, thumbY))
        }

    var onColorChanged: ((Int) -> Unit)? = null

    private var thumbX = 0.7f  // 0..1
    private var thumbY = 0.3f  // 0..1

    // Lớp 1: white → hue (horizontal) — thay đổi khi hue đổi
    private val huePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Lớp 2: transparent → black (vertical) — cố định, không cần rebuild
    private val blackPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val thumbFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val thumbRingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 6f
        color = Color.WHITE
    }
    private val thumbShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        color = Color.parseColor("#55000000")
    }

    private val gradientRect = RectF()
    private val cornerRadius = 24f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        gradientRect.set(0f, 0f, w.toFloat(), h.toFloat())
        buildGradient()
    }

    private fun buildGradient() {
        if (width == 0 || height == 0) return

        // Lớp 1: WHITE (trái) → màu hue thuần (phải)
        val hueColor = Color.HSVToColor(floatArrayOf(hue, 1f, 1f))
        huePaint.shader = LinearGradient(
            0f, 0f, width.toFloat(), 0f,
            Color.WHITE, hueColor,
            Shader.TileMode.CLAMP
        )

        // Lớp 2: TRANSPARENT (trên) → BLACK (dưới) — chỉ cần build 1 lần
        if (blackPaint.shader == null) {
            blackPaint.shader = LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                Color.TRANSPARENT, Color.BLACK,
                Shader.TileMode.CLAMP
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Lớp 1: hue gradient (thay đổi theo hue slider)
        canvas.drawRoundRect(gradientRect, cornerRadius, cornerRadius, huePaint)
        // Lớp 2: black overlay (cố định)
        canvas.drawRoundRect(gradientRect, cornerRadius, cornerRadius, blackPaint)

        // Vẽ thumb
        val tx = thumbX * width
        val ty = thumbY * height
        val thumbRadius = 34f

        thumbFillPaint.color = getColorAt(thumbX, thumbY)

        canvas.drawCircle(tx, ty, thumbRadius + 2f, thumbShadowPaint)
        canvas.drawCircle(tx, ty, thumbRadius, thumbRingPaint)
        canvas.drawCircle(tx, ty, thumbRadius - 3f, thumbFillPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                thumbX = (event.x / width).coerceIn(0f, 1f)
                thumbY = (event.y / height).coerceIn(0f, 1f)
                invalidate()
                onColorChanged?.invoke(getColorAt(thumbX, thumbY))
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun setThumbFromHsv(saturation: Float, value: Float) {
        thumbX = saturation.coerceIn(0f, 1f)
        thumbY = (1f - value).coerceIn(0f, 1f)
        invalidate()
    }

    fun getSelectedColor(): Int = getColorAt(thumbX, thumbY)

    private fun getColorAt(rx: Float, ry: Float): Int {
        val s = rx        // saturation: 0 (trái, trắng) → 1 (phải, hue thuần)
        val v = 1f - ry   // value:      1 (trên, sáng)  → 0 (dưới, đen)
        return Color.HSVToColor(floatArrayOf(hue, s, v))
    }
}