package com.truongnt.fsd.fsd_base_and.Base

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import com.ezt.ai.story.maker.extensions.hideBottomNav
import com.truongnt.fsd.fsd_base_and.utils.ThemeManager
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.truongnt.fsd.fsd_base_and.Base.rate.RateAppDialog
import com.truongnt.fsd.fsd_base_and.R
import com.truongnt.fsd.fsd_base_and.tool.languageTool.LanguageUtil
import com.truongnt.fsd.fsd_base_and.tool.sharePreferenceTool.SharePrefUtils

abstract class BaseActivity<b : ViewBinding> : AppCompatActivity() {
    lateinit var binding: b
    lateinit var manager: ReviewManager
    lateinit var reviewInfo: ReviewInfo
    abstract val setViewBinding: b
    private var isResume = true

    companion object {
        const val SCREEN_ORIENTATION = "SCREEN_ORIENTATION"
        const val PREV_SCREEN_DURATION = "PreScreenDuration"
        const val PREV_SCREEN_ADS = "PreScreenAds"
        const val PREV_SCREEN_NAME = "PreScreenName"
    }

    var timeStayed = 0L

    //setupView here
    abstract fun initView()

    //listen to user action here
    abstract fun viewListener()

    //listen to database change here
    abstract fun dataObservable()

    val internetBroadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.net.conn.CONNECTIVITY_CHANGE" || intent?.action == "android.net.wifi.WIFI_STATE_CHANGED") {
                Log.e("No_internet_start", "nointernet")

//                if (!haveNetworkConnection() && this@BaseActivity !is NoInternetAct && isResume){
//                    Log.e("No_internet_start", "show")
//                    SharePrefUtils.putBoolean(context,"NO_INTER_NET_ON",true)
//                    launchActivity<NoInternetAct> { }
//                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LanguageUtil.setLocale(this)
        super.onCreate(savedInstanceState)

        val orientation = intent.getBooleanExtra(SCREEN_ORIENTATION, false)
        if (orientation) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        binding = setViewBinding
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

            // Adjust the padding/margin of your button when the keyboard is visible
            view.updatePadding(bottom = if (imeVisible) imeHeight else 0)

            insets
        }

        ThemeManager.initTheme(this)
        timeStayed = System.currentTimeMillis()
        initView()
        viewListener()
        dataObservable()

//        if (this !is NoIntern`etAct && this !is SplashAct){
//            val intent = IntentFilter()
//            intent.addAction("android.net.conn.CONNECTIVITY_CHANGE")
//            intent.addAction("android.net.wifi.WIFI_STATE_CHANGED")
//            registerReceiver(internetBroadcast, intent)
//        }
    }

    override fun onResume() {
        super.onResume()
        isResume = true
        window?.hideBottomNav(binding.root)
    }

    override fun onStop() {
        super.onStop()
        isResume = false
    }


    fun Activity.getPreScreenDuration(): Long =
        intent.extras?.getLong(PREV_SCREEN_DURATION, 0L) ?: 0L

    fun Activity.getPreScreenName(): String = intent.extras?.getString(PREV_SCREEN_NAME) ?: ""

    fun Activity.getPreScreenAdsTime(): Long {
        return intent.extras?.getLong(PREV_SCREEN_ADS, 0L) ?: 0L
    }

    fun showRateAppDialog(isNotnow: Boolean, reateSuccess: () -> Unit) {
        val dialog = RateAppDialog(this, isNotnow)
        dialog.onSend = {
            Toast.makeText(
                this@BaseActivity, getString(R.string.rate_success), Toast.LENGTH_SHORT
            ).show()
            SharePrefUtils.forceRated(this@BaseActivity)
            reateSuccess.invoke()
        }

        dialog.onRate = {
            manager = ReviewManagerFactory.create(this@BaseActivity)
            val request: Task<ReviewInfo> = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    reviewInfo = task.result
                    val flow: Task<Void> = manager.launchReviewFlow(this@BaseActivity, reviewInfo)
                    flow.addOnSuccessListener {
                        SharePrefUtils.forceRated(this@BaseActivity)
                        reateSuccess.invoke()
                    }
                }
            }
        }
        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
//        if (this !is NoInternetAct && this !is SplashAct) {
//            unregisterReceiver(internetBroadcast)
//        }
    }

    fun showRateAtStore() {
        manager = ReviewManagerFactory.create(this@BaseActivity)
        val request: Task<ReviewInfo> = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                reviewInfo = task.result
                val flow: Task<Void> = manager.launchReviewFlow(this@BaseActivity, reviewInfo)
                flow.addOnSuccessListener {
                    Log.e("Rateappppp", "showRateAtStore")
                    SharePrefUtils.forceRated(this@BaseActivity)
                }

                flow.addOnCompleteListener { rateTask ->
                    if (!rateTask.isSuccessful) {
                        Log.e("Rateappppp", "failed")
                    } else {
                        Log.e("Rateappppp", "success")
                    }
                }
            }
        }
    }

    fun openAppInPlayStore() {
        try {
            // ✅ Mở bằng Google Play app
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
        } catch (e: ActivityNotFoundException) {
            // ❌ Nếu không có Google Play → mở bằng trình duyệt web
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
        }
    }
}