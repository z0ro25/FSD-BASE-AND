package com.ezt.skipping.makemoney.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun <T> CoroutineScope.executeAsync(
    onPreExecute: suspend () -> Unit,
    doInBackground: suspend () -> T,
    onPostExecute: (T) -> Unit
) = launch {
    onPreExecute()
    val result = withContext(Dispatchers.IO) {
        doInBackground()
    }
    onPostExecute(result)
}

fun haveNetworkConnection(context: Context): Boolean {
    var haveConnectedWifi = false
    var haveConnectedMobile = false
    val cm =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.allNetworkInfo
    for (ni in netInfo) {
        if (ni.typeName.equals("WIFI", ignoreCase = true))
            if (ni.isConnected) haveConnectedWifi = true
        if (ni.typeName.equals("MOBILE", ignoreCase = true))
            if (ni.isConnected) haveConnectedMobile = true
    }
    return haveConnectedWifi || haveConnectedMobile
}

fun getKeyboardHeight(
    rootView: View?,
    keyBoardActive: (Int) -> Unit,
    keyBoardNotActive: (Int) -> Unit
) {
    rootView?.viewTreeObserver?.addOnGlobalLayoutListener {
        val rect = Rect()
        rootView.getWindowVisibleDisplayFrame(rect)
        val screenHeight = rootView.height
        val keyboardHeight = screenHeight - (rect.bottom - rect.top)
        if (keyboardHeight > screenHeight / 4) {
            keyBoardActive.invoke(keyboardHeight)
        } else {
            keyBoardNotActive.invoke(keyboardHeight)
        }
    }
}
fun setMargins(v: ViewGroup, l: Int, t: Int, r: Int, b: Int) {
    val relativeParams = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    )
    relativeParams.setMargins(l, t, r, b)
    v.layoutParams = relativeParams
    v.requestLayout()
}

fun showKeyboard(edt: EditText?, context: Context){
    edt?.requestFocus()
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun hideKeyboard(edt: EditText?, context: Context){
    edt?.clearFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}