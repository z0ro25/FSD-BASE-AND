package com.truongnt.fsd.fsd_base_and.features.lang

import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezt.skipping.makemoney.extensions.launchActivity
import com.ezt.skipping.makemoney.extensions.tap
import com.google.android.gms.ads.nativead.NativeAd
import com.truongnt.fsd.fsd_base_and.Base.BaseActivity
import com.truongnt.fsd.fsd_base_and.databinding.ActivityLanguageStartBinding
import com.truongnt.fsd.fsd_base_and.features.onb.OnbAct
import com.truongnt.fsd.fsd_base_and.models.LanguageModelStart
import com.truongnt.fsd.fsd_base_and.tool.languageTool.LanguageUtil
import com.truongnt.fsd.fsd_base_and.utils.RemoteConfigUtils
import java.util.Locale


class LanguageStartActivity : BaseActivity<ActivityLanguageStartBinding>() {

    var codeLang = ""
    var listLanguage: ArrayList<LanguageModelStart>? = arrayListOf()

    val listener = IClickItemLanguage { code ->
        codeLang = code

        val isAdsEnalbe =
            RemoteConfigUtils.getRemoteConfigBoolean(this, RemoteConfigUtils.ADS_ENABLE)
                    || (RemoteConfigUtils.getRemoteConfigBoolean(
                this,
                RemoteConfigUtils.NATIVE_LANGUAGE1
            )
                    && RemoteConfigUtils.getRemoteConfigBoolean(
                this,
                RemoteConfigUtils.NATIVE_LANGUAGE2
            ))


    }

    val adapter by lazy { LanguageStartAdapter(listLanguage, this, listener) }
    private var languageModel: LanguageModelStart? = null


    companion object {
        var nativeFullOnb: NativeAd? = null
    }

    //todo small native ads
    override val setViewBinding: ActivityLanguageStartBinding
        get() = ActivityLanguageStartBinding.inflate(LayoutInflater.from(this))

    override fun initView() {
        Log.e("ạkshdfkjhasdjkfk", "language start")
//        TrackingManager.logEventScreenGo(
//            TrackingManager.mappingScreenName(getPreScreenName()),
//            TrackingManager.mappingScreenName(LanguageStartActivity::class.java.simpleName),
//            getPreScreenAdsTime(),
//            getPreScreenDuration()
//        )

        codeLang = LanguageUtil.getPreLanguage(this)
        val linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter

//        languageAdapter.setCheck(codeLang)
//        Log.d("qqqqqqqqqqq", "codeLang: $codeLang")

        val isAdsEnalbe =
            RemoteConfigUtils.getRemoteConfigBoolean(this, RemoteConfigUtils.ADS_ENABLE)
                    || (RemoteConfigUtils.getRemoteConfigBoolean(
                this,
                RemoteConfigUtils.NATIVE_LANGUAGE1
            )
                    && RemoteConfigUtils.getRemoteConfigBoolean(
                this,
                RemoteConfigUtils.NATIVE_LANGUAGE2
            ))

        initData()

        binding.ivTick.tap { v ->
            LanguageUtil.saveLocale(baseContext, codeLang)
            launchActivity<OnbAct>() { }
        }

        binding.btnSave.setOnClickListener {
            LanguageUtil.saveLocale(baseContext, codeLang)
            launchActivity<OnbAct>() { }
        }


    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadAds() {
    }

    override fun viewListener() {

    }

    override fun dataObservable() {

    }

    private fun initData() {
        listLanguage = arrayListOf()
        codeLang =
            if (LanguageUtil.getPreLanguage(this) == null || LanguageUtil.getPreLanguage(this)
                    .isEmpty()
            ) Locale.getDefault().language else LanguageUtil.getPreLanguage(this)
        Log.d("qqqqqqqqqqq", "codeLang: $codeLang")
        listLanguage!!.add(LanguageModelStart("English", "en"))
        listLanguage!!.add(LanguageModelStart("Hindi", "hi"))
        listLanguage!!.add(LanguageModelStart("Spanish", "es"))
        listLanguage!!.add(LanguageModelStart("French", "fr"))
        listLanguage!!.add(LanguageModelStart("German", "de"))
        listLanguage!!.add(LanguageModelStart("Korean", "ko"))
        listLanguage!!.add(LanguageModelStart("Portuguese", "pt"))
        listLanguage!!.add(LanguageModelStart("Arabic", "ar"))

//        for (i in listLanguage!!.indices) {
//            if (listLanguage!![i].code.equals(codeLang)) {
//                listLanguage!!.add(0, listLanguage!![i])
//                listLanguage!!.removeAt(i + 1)
//                isDevice = true
//            }
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}