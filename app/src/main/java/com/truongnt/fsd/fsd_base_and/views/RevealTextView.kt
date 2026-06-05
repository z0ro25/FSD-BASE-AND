package com.ezt.ai.story.maker.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class RevealTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    // Biến điều khiển: 0 là ẩn hết, 1 là hiện hết
    var revealPercent: Float = 1f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate() // Vẽ lại mỗi khi giá trị thay đổi
        }

    override fun onDraw(canvas: Canvas) {
        // 1. Tạo một cái khung hình chữ nhật
        // Khung này bắt đầu từ 0 (đỉnh) đến chiều cao hiện tại nhân với %
        canvas.save()
        canvas.clipRect(0, 0, width, (height * revealPercent).toInt())

        // 2. Gọi super để TextView tự vẽ chữ, màu, size như bình thường
        // Nhưng nó chỉ hiển thị được trong phạm vi clipRect ở trên
        super.onDraw(canvas)

        canvas.restore()
    }
}