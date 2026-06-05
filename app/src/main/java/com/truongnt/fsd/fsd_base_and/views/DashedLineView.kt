package com.ezt.ai.story.maker.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.ezt.ai.story.maker.R

class DashedLineView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint: Paint = Paint()

    init {
        // Cấu hình Paint cho đường thẳng
        val a = context.obtainStyledAttributes(attrs, R.styleable.DashedLineView)

        // Lấy giá trị từ các thuộc tính tùy chỉnh
        val lineColor = a.getColor(R.styleable.DashedLineView_lineColor, Color.RED)
        val dashLength = a.getDimension(R.styleable.DashedLineView_dashLength, 20f)
        val dashGap = a.getDimension(R.styleable.DashedLineView_dashGap, 10f)

        paint.color = lineColor
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE

        val dashEffect = DashPathEffect(floatArrayOf(dashLength, dashGap), 0f)
        paint.pathEffect = dashEffect

        a.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(0f, height.toFloat()/2, width.toFloat(), height.toFloat()/2, paint)
    }
}
