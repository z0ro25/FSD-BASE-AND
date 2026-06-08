package com.truongnt.fsd.fsd_base_and.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.truongnt.fsd.fsd_base_and.R
import com.truongnt.fsd.fsd_base_and.tool.sharePreferenceTool.SharePrefUtils
import kotlinx.serialization.json.Json

object RemoteConfigUtils {
    const val INTER_SPLASH = "inter_splash"
    const val NATIVE_LANGUAGE1 = "native_language1"
    const val NATIVE_LANGUAGE2 = "native_language2"
    const val NATIVE_ONB1 = "native_onb1"
    const val NATIVE_ONB2 = "native_onb2"
    const val NATIVE_ONB3 = "native_onb3"
    const val NATIVE_ONB_FULL = "native_onb_full"
    const val NATIVE_PERMISSION = "native_permisson"
    const val INTER_START = "inter_start"
    const val INTER_INAPP = "inter_inapp"
    const val NATIVE_IN_APP = "native_in_app"
    const val BANNER_IN_APP = "banner_in_app"
    const val OPEN_ADS = "open_ads"
    const val ADS_ENABLE = "ads_enable"
    const val INTER_DELAY = "inter_delay"
    const val STAR_VALUE = "star_value"
    const val INT_SPLASH_TIME_OUT = "int_splash_times_out"
    const val LEADERBOARD_REWARD = "leaderboard_reward"
    const val NATIVE_LANG_SMALL = "native_language_small"
    const val DAILY_STEP_GOAL = "daily_step_goal"
    const val START_COIN = "start_coin"
    const val GEN_STORY_COIN = "gen_story_coin"
    const val TURN_STORY_COIN = "turn_story_coin"
    const val T1_COUNTRIES = "T1_countries"
    const val IAP_PACK_COIN = "iap_pack_coint"
    const val NATIVE_CLP = "nt_clp"

    fun initRemoteConfig(listener: OnCompleteListener<Boolean>) {
        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        mFirebaseRemoteConfig.reset()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(listener)
    }

    private fun getRemoteConfigBoolean(adUnitId: String?): Boolean {
        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        return mFirebaseRemoteConfig.getBoolean(adUnitId!!)
    }

    private fun getRemoteConfigLong(adUnitId: String?): Long {
        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        return mFirebaseRemoteConfig.getLong(adUnitId!!)
    }

    private fun getRemoteConfigInt(adUnitId: String?): Int {
        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        return mFirebaseRemoteConfig.getLong(adUnitId!!).toInt()
    }

    public fun getRemoteConfigString(adUnitId: String?): String {
        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val `object` = mFirebaseRemoteConfig.getString(adUnitId!!)
        return `object`
    }

    private fun getRemoteConfigFloat(adUnitId: String?): Float {
        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        return mFirebaseRemoteConfig.getDouble(adUnitId!!).toFloat()
    }

    fun saveRemoteConfigInt(context: Context, adUnitId: String?) {
        SharePrefUtils.putInteger(
            context,
            adUnitId,
            getRemoteConfigInt(adUnitId)
        )
    }

    fun saveRemoteConfigBoolean(context: Context, adUnitId: String?) {
        SharePrefUtils.putBoolean(
            context,
            adUnitId,
            getRemoteConfigBoolean(adUnitId)
        )
    }

    fun saveRemoteConfigLong(context: Context, adUnitId: String?) {
        SharePrefUtils.putlong(
            context,
            adUnitId,
            getRemoteConfigLong(adUnitId)
        )
    }

    fun saveRemoteConfigString(context: Context, adUnitId: String?) {
        SharePrefUtils.putString(
            context,
            adUnitId,
            getRemoteConfigString(adUnitId)
        )
    }

    fun saveRemoteConfigFloat(context: Context, adUnitId: String?) {
        SharePrefUtils.putFloat(
            context,
            adUnitId,
            getRemoteConfigFloat(adUnitId)
        )
    }

    fun getRemoteConfigBoolean(context: Context, adUnitId: String?): Boolean {
        return SharePrefUtils.getBoolean(
            context,
            adUnitId,
            true
        )
    }

    fun getRemoteConfigLong(context: Context, adUnitId: String?): Long {
        return SharePrefUtils.getLong(
            context,
            adUnitId,
            getRemoteConfigLong(adUnitId)
        )
    }

    fun getRemoteConfigFloat(context: Context, adUnitId: String?): Float {
        return SharePrefUtils.getFloat(
            context,
            adUnitId,
            getRemoteConfigFloat(adUnitId)
        )
    }

    fun getRemoteConfigInt(context: Context, adUnitId: String?): Int {
        return SharePrefUtils.getInteger(
            context,
            adUnitId,
            getRemoteConfigInt(adUnitId)
        )
    }

    fun getRemoteConfigString(context: Context, adUnitId: String?): String {
        return SharePrefUtils.getString(
            context,
            adUnitId,
            getRemoteConfigString(adUnitId)
        )
    }

}
