package com.ezt.ai.story.maker.extensions

import android.os.Build
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.util.Objects

fun Window.hideNavigation() {
    if (setFullScreenWallpaper()) return

    decorView.viewTreeObserver.addOnGlobalLayoutListener {
        if (setFullScreenWallpaper()) return@addOnGlobalLayoutListener
    }
}

private fun Window.setFullScreenWallpaper(): Boolean {
    val windowInsetsController: WindowInsetsControllerCompat? =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            ViewCompat.getWindowInsetsController(decorView)
        } else {
            WindowInsetsControllerCompat(this, decorView)
        }

    if (windowInsetsController == null) {
        return true
    }
    setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
    windowInsetsController.hide(WindowInsetsCompat.Type.systemGestures())
    return false
}

fun Window.hideBottomNav(view : View){
    val windowInsetsController: WindowInsetsControllerCompat?
    windowInsetsController = if (Build.VERSION.SDK_INT >= 30) {
        ViewCompat.getWindowInsetsController(decorView)
    } else WindowInsetsControllerCompat(this,view)

    if (windowInsetsController == null) {
        return
    }
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

    decorView.setOnSystemUiVisibilityChangeListener { i: Int ->
        if (i == 0) {
            Handler().postDelayed({
                val windowInsetsController1: WindowInsetsControllerCompat?
                windowInsetsController1 = if (Build.VERSION.SDK_INT >= 30) {
                    ViewCompat.getWindowInsetsController(decorView)
                } else {
                    WindowInsetsControllerCompat(this, view)
                }
                Objects.requireNonNull(windowInsetsController1)?.hide(WindowInsetsCompat.Type.navigationBars())
            }, 3000)
        }
    }
}




