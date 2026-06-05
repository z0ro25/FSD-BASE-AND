package com.ezt.ai.story.maker.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ezt.ai.story.maker.R
import kotlin.apply

class StrokeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {


    private var strokeColor: Int = Color.BLACK
    private var strokeWidth: Float = 0f
    private val bounds = Rect()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StrokeTextView,
            0, 0
        ).apply {
            try {
                strokeColor = getColor(R.styleable.StrokeTextView_strokeColor, Color.BLACK)
                strokeWidth = getDimension(R.styleable.StrokeTextView_strokeWidth, 0f)
            } finally {
                recycle()
            }
        }
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
//        val originalTextColor = currentTextColor
//        val text = text.toString()
//
//        // get text bounds
//        paint.getTextBounds(text, 0, text.length, bounds)
//
//        // adjust stroke paint
//        val strokePaint = Paint(paint)
//        strokePaint.style = Paint.Style.STROKE
//        strokePaint.strokeWidth = strokeWidth
//        strokePaint.color = strokeColor
//        strokePaint.isAntiAlias = true
//
//        val x = (width - paint.measureText(text)) / 2f
//        val y = (height + bounds.height()) / 2f
//
//        // draw stroke
//        canvas.drawText(text, x, y, strokePaint)
//
//        // draw fill
//        paint.style = Paint.Style.FILL
//        paint.color = originalTextColor
//        canvas.drawText(text, x, y, paint)


        val text = text.toString()
        val metrics = paint.fontMetrics

        // Tính toán tọa độ X (căn giữa theo chiều ngang)
        val x = (width - paint.measureText(text)) / 2f

        // Tính toán tọa độ Y (căn giữa theo chiều dọc dựa trên Baseline)
        // Công thức: (Chiều cao view / 2) - ((top + bottom) / 2)
        val y = (height / 2f) - ((metrics.ascent + metrics.descent) / 2f)

        val originalTextColor = currentTextColor

        // Vẽ Stroke trước
        val strokePaint = Paint(paint)
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = strokeWidth
        strokePaint.color = strokeColor
        strokePaint.isAntiAlias = true
        canvas.drawText(text, x, y, strokePaint)

        // Vẽ Fill (chữ chính) đè lên trên
        paint.style = Paint.Style.FILL
        paint.color = originalTextColor
        canvas.drawText(text, x, y, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val text = text.toString()
//        paint.getTextBounds(text, 0, text.length, bounds)
//        val extra = strokeWidth.toInt()
//        val desiredWidth = (paint.measureText(text) + extra * 2).toInt()
//        val desiredHeight = (bounds.height() + extra * 2)
//        setMeasuredDimension(desiredWidth, desiredHeight)

        val text = text.toString()

        // Sử dụng measureText để lấy độ rộng chính xác của text
        val textWidth = paint.measureText(text)

        // FontMetrics cho biết độ cao chính xác của font (bao gồm phần trên và dưới baseline)
        val metrics = paint.fontMetrics
        val textHeight = Math.abs(metrics.top) + Math.abs(metrics.bottom)

        val extra = strokeWidth.toInt()

        // Thêm padding cho stroke ở cả 2 bên và trên dưới
        val desiredWidth = (textWidth + extra * 2 + paddingLeft + paddingRight).toInt()
        val desiredHeight = (textHeight + extra * 2 + paddingTop + paddingBottom).toInt()

        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    fun setStrokeColor(color: Int) {
        if (this.strokeColor != color) {
            this.strokeColor = color
            invalidate() // Yêu cầu vẽ lại View với màu mới
        }
    }
} 