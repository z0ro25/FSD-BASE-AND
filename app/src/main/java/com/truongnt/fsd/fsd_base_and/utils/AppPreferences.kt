package com.truongnt.fsd.fsd_base_and.utils

import android.content.Context
import com.truongnt.fsd.fsd_base_and.tool.sharePreferenceTool.SharePrefUtils

object AppPreferences {

    var nameScreen: String = ""


    fun increaseInterCount(context: Context) {
        var count = SharePrefUtils.getInteger(context, "inter_count", 0)
        count++
        SharePrefUtils.putInteger(context, "inter_count", count)
    }

    fun getInterCount(context: Context): Int = SharePrefUtils.getInteger(context, "inter_count", 0)

    fun increaseRewardCount(context: Context) {
        var count = SharePrefUtils.getInteger(context, "reward_count", 0)
        count++
        SharePrefUtils.putInteger(context, "reward_count", count)
    }

    fun getRewardCount(context: Context): Int =
        SharePrefUtils.getInteger(context, "reward_count", 0)

    fun increaseNativeCount(context: Context) {
        var count = SharePrefUtils.getInteger(context, "native_count", 0)
        count++
        SharePrefUtils.putInteger(context, "native_count", count)
    }

    fun getNativeCount(context: Context): Int =
        SharePrefUtils.getInteger(context, "native_count", 0)

    fun increaseAdLtv(context: Context, value: Float) {
        var count = SharePrefUtils.getFloat(context, "ad_ltv", 0f)
        count += value
        SharePrefUtils.putFloat(context, "ad_ltv", count)
    }

    fun getAdLtv(context: Context): Float = SharePrefUtils.getFloat(context, "ad_ltv", 0f)

    fun getScreenIndex(context: Context): Int =
        SharePrefUtils.getInteger(context, "screen_index", 0)

    fun increaseScreenIndex(context: Context) {
        var count = SharePrefUtils.getInteger(context, "screen_index", 0)
        count++
        SharePrefUtils.putInteger(context, "screen_index", count)
    }
}