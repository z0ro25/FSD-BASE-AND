package com.ezt.skipping.makemoney.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.temporal.WeekFields
import java.util.*

/*
*  Lưu ý đây là file mở rộng nếu cần dùng extention mới hãy thêm mới ở dưới
*  Đừng sửa những hàm đã có sẵn
*  Xin cảm ơn
* */


fun CharSequence?.isValidEmail(): Boolean {
    if (this.isNullOrBlank()) return false
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

//view hiển thị
fun View.makeVisible() {
    visibility = View.VISIBLE
}

//view tàng hình
fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

//view ẩn
fun View.makeGone() {
    visibility = View.GONE
}

//convert dp qua px
fun dpToPx(dp: Int, context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()

//inflate view
internal fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return context.layoutInflater.inflate(layoutRes, this, attachToRoot)
}

//hàm lấy layoutinflater
internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

//lấy inputmethodmanager
internal val Context.inputMethodManager
    get() = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

//lấy drawable sử dụng contextcompat
internal fun Context.getDrawableCompat(@DrawableRes drawable: Int) =
    ContextCompat.getDrawable(this, drawable)

//lấy màu sử sụng contextcompat
internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

//set màu text bằng màu trong res
internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))

//lấy ngày trong tuần ở khu vực
@RequiresApi(Build.VERSION_CODES.O)
fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.ENGLISH).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}

//set bo góc
fun GradientDrawable.setCornerRadius(
    topLeft: Float = 0F,
    topRight: Float = 0F,
    bottomRight: Float = 0F,
    bottomLeft: Float = 0F
) {
    cornerRadii = arrayOf(
        topLeft, topLeft,
        topRight, topRight,
        bottomRight, bottomRight,
        bottomLeft, bottomLeft
    ).toFloatArray()
}

//conver số 1000 => 1,000
fun Int.convertToThousandNum(): String {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###")
    return formatter.format(this)
}

//đổi từ ml => floz
fun Int.convertMltoFloz(): Float {
    return (this * 0.03).toFloat()
}

//đổi từ conver số 1.000 => 1000
fun String.convertToIntNum(): Int {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###")
    return formatter.parse(this).toInt()
}

//check ngày hiện tại
fun Long.isToday(): Boolean {
    val today = Calendar.getInstance()
    val day = Calendar.getInstance()
    day.timeInMillis = this
    return day.get(Calendar.YEAR) == today.get(Calendar.YEAR)
            && day.get(Calendar.MONTH) == today.get(Calendar.MONTH)
            && day.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
}

fun Long.isInCurrentMonth( zone: ZoneId = ZoneId.systemDefault()): Boolean {
    val ymNow = YearMonth.now(zone)
    val ymTs  = YearMonth.from(Instant.ofEpochMilli(this).atZone(zone).toLocalDate())
    return ymTs == ymNow
}

//đổi từ Km => Miles
fun Float.convertKmsToMiles(): Float {
    return (0.621371 * this).toFloat()
}

//làm tròn lên từ 0.5
fun Float.roundUper(): Int {
    val c = (this + 0.5f).toInt()
    val n = this + 0.5f
    return if ((n - c) % 2 == 0f) this.toInt() else c
}

//convert date string => time long
fun String.convertToTimemilis(): Long {
    val strDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    return strDateFormat.parse(this).time
}

//hàm này chánh vc double tap
fun View.tap(action: (view: View?) -> Unit) {
    setOnClickListener {
        it.isEnabled = false
        it.postDelayed({ it.isEnabled = true }, 1500)
        action(it)
    }
}

//ẩn bàn phím
fun View.hideKeyboard() {
    val context = this.context ?: return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    val context = this.context ?: return
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}



fun View.getBitmapFromView(): Bitmap {
    val returnedBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(returnedBitmap)
    val bgDrawable = this.background
    if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
    this.draw(canvas)
    return returnedBitmap
}

fun SeekBar.onSeekChangeListener(onchange: (SeekBar, Int, Boolean) -> Unit) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (seekBar != null) {
                onchange.invoke(seekBar, progress, fromUser)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    })
}

fun Context.getColor(id: Int) : Int{
    return ContextCompat.getColor(this,id)
}

fun Context.getDeviceCountry(): String? {
    val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    // 1. Lấy mã quốc gia từ SIM (nếu có lắp SIM)
    val simCountry = tm.simCountryIso

    // 2. Lấy mã quốc gia từ mạng di động đang kết nối (thời gian thực)
    val networkCountry = tm.networkCountryIso
    val code = simCountry.takeIf { it.isNotBlank() } ?: networkCountry.takeIf { it.isNotBlank() }
    // Ưu tiên lấy từ SIM, nếu không có thì lấy từ mạng
    Log.e("CountriesCode", code ?: "")
    return code
}

@SuppressLint("ResourceType")
fun ImageView.applyStyle(styleResId: Int) {
    val attrs = intArrayOf(android.R.attr.tint, android.R.attr.padding) // Danh sách các item bạn có trong style
    val typedArray = context.obtainStyledAttributes(styleResId, attrs)

    // Lấy màu tint từ style
    val tintColor = typedArray.getColorStateList(0) // 0 là vị trí của android.R.attr.tint trong mảng trên
    if (tintColor != null) {
        this.imageTintList = tintColor
    }

    // Lấy padding từ style
    val padding = typedArray.getDimensionPixelSize(1, 0)
    setPadding(padding, padding, padding, padding)

    typedArray.recycle()
}
