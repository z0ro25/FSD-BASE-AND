
package com.ezt.ai.story.maker.views // Thay thế bằng package thực tế của bạn

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.cardview.widget.CardView
import com.google.android.material.appbar.AppBarLayout

/**
 * Behavior tùy chỉnh để một View (ở đây là CardView) di chuyển theo 
 * độ dịch chuyển của AppBarLayout.
 * * Target Type là CardView - View mà Behavior này được gắn vào.
 */
class CustomFollowBehavior(context: Context, attrs: AttributeSet?) 
    : CoordinatorLayout.Behavior<CardView>(context, attrs) {

    // 1. Khai báo view mà Target View sẽ "phụ thuộc" vào
    override fun layoutDependsOn(
        parent: CoordinatorLayout, 
        child: CardView, // View đang được điều khiển (Target)
        dependency: View // View mà Target phụ thuộc
    ): Boolean {
        // Ta muốn CardView phụ thuộc vào AppBarLayout
        return dependency is AppBarLayout 
    }

    // 2. Định nghĩa hành vi khi View phụ thuộc thay đổi (tức là AppBarLayout cuộn)
    override fun onDependentViewChanged(
        parent: CoordinatorLayout, 
        child: CardView, 
        dependency: View
    ): Boolean {
        val appBarLayout = dependency as AppBarLayout
        
        // Lấy vị trí Y hiện tại của AppBarLayout.
        // Khi cuộn lên, top sẽ là số âm (ví dụ: -50)
        val currentOffset = appBarLayout.top.toFloat()
        
        // --- LOGIC ĐIỀU KHIỂN ---
        
        // Thiết lập độ dịch chuyển Y (translationY) cho CardView
        // Khi AppBarLayout cuộn lên 50px (top = -50), CardView cũng dịch chuyển lên -50px.
        // Điều này khiến CardView "dính" vào và cuộn cùng AppBar.
        child.translationY = currentOffset

        // (Tùy chọn) Nếu bạn muốn thêm hiệu ứng:
        // Ví dụ: Làm mờ CardView khi nó cuộn lên
        // val maxScroll = appBarLayout.height - (appBarLayout.minimumHeight)
        // val ratio = 1.0f - (currentOffset / maxScroll.toFloat()) // Tỷ lệ từ 0 đến 1
        // child.alpha = ratio.coerceAtLeast(0.5f) // Giữ alpha tối thiểu là 0.5f

        // Trả về true báo hiệu rằng layout đã thay đổi
        return true
    }
}