package com.ezt.ai.story.maker.views

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TypewriterTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    private val mHandler = Handler(Looper.getMainLooper())
    private var isTyping = false
    private val cursorChar = "_" // Bạn có thể thay bằng "_" hoặc "|"

    // Lưu trữ trạng thái để resume
    private var currentFullText: CharSequence = ""
    private var currentIndex: Int = 0
    private var savedOnTyping: ((Int) -> Unit)? = null
    private var savedOnFinished: (() -> Unit)? = null

    private val typingRunnable = object : Runnable {
        override fun run() {
            if (currentIndex <= currentFullText.length) {
                currentIndex += 3
                val safeIndex = if (currentIndex <= currentFullText.length) currentIndex else currentFullText.length
                val currentText = currentFullText.subSequence(0, safeIndex)

                text = if (currentIndex <= currentFullText.length) "$currentText$cursorChar" else currentText

                mHandler.postDelayed(this, 40)
                savedOnTyping?.invoke(currentIndex)
            } else {
                isTyping = false
                startBlinkingEffect(savedOnFinished ?: {})
            }
        }
    }

    fun animateText(
        txt: CharSequence,
        startIndex: Int,
        onTyping: (Int) -> Unit,
        onFinished: () -> Unit
    ) {
        // Gán dữ liệu vào biến toàn cục để có thể resume
        this.currentFullText = txt
        this.currentIndex = startIndex
        this.savedOnTyping = onTyping
        this.savedOnFinished = onFinished

        mHandler.removeCallbacksAndMessages(null)
        isTyping = true
        mHandler.post(typingRunnable)
    }

    // Hàm tạm dừng: Chỉ ngừng Handler, không xóa dữ liệu lưu trữ
    fun pauseAnimation() {
        isTyping = false
        mHandler.removeCallbacksAndMessages(null)
    }

    // Hàm chạy tiếp: Dùng dữ liệu đã lưu để gõ tiếp từ currentIndex
    fun resumeAnimation() {
        if (!isTyping && currentFullText.isNotEmpty() && currentIndex < currentFullText.length) {
            isTyping = true
            mHandler.post(typingRunnable)
        }
    }

    private fun startBlinkingEffect(onFinished: () -> Unit) {
        val fullText = text.toString().removeSuffix(cursorChar).trim()
        var count = 0
        val maxBlinds = 3

        val blinkRunnable = object : Runnable {
            override fun run() {
                if (count < maxBlinds) {
                    text = if (count % 2 == 0) "$fullText " else "$fullText$cursorChar"
                    count++
                    mHandler.postDelayed(this, 300)
                } else {
                    text = fullText
                    onFinished()
                }
            }
        }
        mHandler.post(blinkRunnable)
    }

    // Khi View bị che khuất trong RecyclerView
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        pauseAnimation() // Tạm dừng thay vì xóa sạch
    }
}