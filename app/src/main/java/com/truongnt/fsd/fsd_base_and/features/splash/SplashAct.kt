package com.truongnt.fsd.fsd_base_and.features.splash

import android.util.Log
import com.ezt.skipping.makemoney.extensions.haveNetworkConnection
import com.ezt.skipping.makemoney.extensions.launchActivity
import com.truongnt.fsd.fsd_base_and.Base.BaseActivity
import com.truongnt.fsd.fsd_base_and.databinding.ActSplashBinding
import com.truongnt.fsd.fsd_base_and.features.lang.LanguageStartActivity
import com.truongnt.fsd.fsd_base_and.utils.GDPRRequestable
import com.truongnt.fsd.fsd_base_and.utils.RemoteConfigUtils
import com.truongnt.fsd.nttads.FsdAds

class SplashAct : BaseActivity<ActSplashBinding>() {
    override val setViewBinding: ActSplashBinding
        get() = ActSplashBinding.inflate(layoutInflater)


    override fun initView() {
        binding.apply {

            requestUmp {
                launchActivity<LanguageStartActivity>()
            }

        }
    }

    override fun viewListener() {

    }

    companion object {
        var deviceIdfsdfdf = 0
    }

    override fun dataObservable() {

    }

    private fun requestUmp(action: () -> Unit) {
        Log.e("ạkshdfkjhasdjkfk", "ump")
        GDPRRequestable.getGdprRequestable(this).setOnRequestGDPRCompleted {
            initRemoteConfig {
                initAds {
                    action.invoke()
                }
            }
        }
        GDPRRequestable.getGdprRequestable(this).requestGDPR()
    }

    private fun initRemoteConfig(action: () -> Unit) {
        Log.e("ạkshdfkjhasdjkfk", "remote")
        RemoteConfigUtils.initRemoteConfig {
            if (haveNetworkConnection()) {
                if (it.isSuccessful) {
                    val update = it.result as Boolean
                    if (update) {

                    }
                }
            }
            action.invoke()
        }
    }

    private var isLoaded = false
    private var timeout = false
    fun initAds(action: () -> Unit) {
        FsdAds.init(this) {
            action.invoke()
        }
    }
}