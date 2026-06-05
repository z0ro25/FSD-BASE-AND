package com.ezt.ai.story.maker.views

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.LinearLayout
import kotlin.math.abs

class BottomUpSlidingLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var initialY: Float = 0f
    private var maxHeight: Int = 0
    private var minHeight: Int = 0
    private val touchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
    private var isDragging: Boolean = false

    init {
        orientation = VERTICAL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Đảm bảo child views được đo đạc kích thước thực tế
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))

        // 1. Tính toán Max Height (Tổng chiều cao của tất cả child views)
        maxHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            maxHeight += child.measuredHeight
        }

        // 2. Xác định Min Height (Chiều cao của child view đầu tiên/Header)
        minHeight = if (childCount > 0) getChildAt(0).measuredHeight else 0

        // 3. Sử dụng chiều cao hiện tại hoặc minHeight để đo đạc chính nó
        val currentHeight = layoutParams.height.let {
            if (it <= 0) minHeight else it
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), currentHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentBottom = height // Bắt đầu từ đáy của ViewGroup

        // Bố trí child views từ dưới lên (Bottom-Up)
        for (i in childCount - 1 downTo 0) {
            val child = getChildAt(i)
            val childHeight = child.measuredHeight

            val left = paddingLeft
            val top = currentBottom - childHeight
            val right = width - paddingRight
            val bottom = currentBottom

            child.layout(left, top, right, bottom)
            currentBottom = top // Cập nhật vị trí bắt đầu của child tiếp theo
        }
    }

    // =================================================================
    //               XỬ LÝ CHẠM VÀ XUNG ĐỘT CUỘN
    // =================================================================

    // Kỹ thuật này được dùng để quyết định khi nào thì ViewGroup (SlidingLayout)
    // nên chặn (intercept) sự kiện chạm thay vì để child view xử lý.
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        // Chỉ chặn nếu người dùng chạm vào khu vực Header (MinHeight area)
        val isTouchInHeader = event.y < minHeight

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialY = event.rawY
                isDragging = false
                // Trả về false để child view xử lý ACTION_DOWN
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaY = event.rawY - initialY

                // Nếu chạm vào Header VÀ đã kéo đủ touchSlop
                if (isTouchInHeader && abs(deltaY) > touchSlop) {
                    // Nếu kéo lên (deltaY < 0) HOẶC kéo xuống (deltaY > 0)
                    // thì chúng ta chặn sự kiện (Trả về true) để ViewGroup xử lý
                    isDragging = true
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
            }
        }

        // Nếu không có drag nào đủ tiêu chuẩn, để child view xử lý
        return isDragging
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (childCount == 0) return super.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                // Chỉ xử lý MOVE nếu đã được đánh dấu là Dragging từ onInterceptTouchEvent
                if (isDragging) {
                    val deltaY = event.rawY - initialY

                    // Cập nhật chiều cao (tính toán tương tự phiên bản trước)
                    var newHeight = layoutParams.height - deltaY.toInt()
                    newHeight = newHeight.coerceIn(minHeight, maxHeight)

                    layoutParams.height = newHeight
                    requestLayout()

                    initialY = event.rawY // Cập nhật vị trí Y
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (isDragging) {
                    performSnapAnimation()
                    isDragging = false
                    return true // Tiêu thụ sự kiện UP/CANCEL
                }
            }
        }

        // Luôn trả về true nếu ACTION_DOWN đã được xử lý (thường là trong onInterceptTouchEvent/onTouchEvent)
        return true
    }

    private fun performSnapAnimation() {
        val currentHeight = layoutParams.height
        // Quyết định trạng thái cuối cùng (Min hoặc Max)
        val targetHeight = if (currentHeight > (maxHeight + minHeight) / 2) {
            maxHeight // Kéo qua điểm giữa -> Mở rộng
        } else {
            minHeight // Dưới điểm giữa -> Thu gọn
        }

        animateToHeight(targetHeight)
    }

    private fun animateToHeight(targetHeight: Int) {
        val startHeight = layoutParams.height
        val animator = ValueAnimator.ofInt(startHeight, targetHeight)

        animator.addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            // Yêu cầu bố trí lại layout sau mỗi lần cập nhật
            requestLayout()
        }

        animator.duration = 300
        animator.start()
    }
}