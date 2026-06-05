package com.ezt.ai.story.maker.views

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout

class RevealLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var revealPercent: Float = 1f

    init {
        // QUAN TRỌNG: Mặc định ViewGroup không gọi onDraw, phải tắt chế độ này đi
        setWillNotDraw(false)
    }

    fun setRevealPercent(percent: Float) {
        revealPercent = percent
        invalidate()
    }

    fun getRevealPercent(): Float = revealPercent

    // Với ViewGroup, ta phải can thiệp vào dispatchDraw thay vì onDraw
    // dispatchDraw là nơi nó vẽ các View con (như TextView bên trong)
    override fun dispatchDraw(canvas: Canvas) {
        canvas.save()
        
        // Cắt toàn bộ khung của Layout
        val clipHeight = height * revealPercent
        canvas.clipRect(0f, 0f, width.toFloat(), clipHeight)

        super.dispatchDraw(canvas)
        
        canvas.restore()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Chạy luôn cho máu
//        ObjectAnimator.ofFloat(this, "revealPercent", 0f, 1f).apply {
//            duration = 1000
//            start()
//        }
    }
}