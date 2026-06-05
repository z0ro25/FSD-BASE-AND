package com.truongnt.fsd.fsd_base_and.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.truongnt.fsd.fsd_base_and.tool.sharePreferenceTool.SharePrefUtils

object ThemeManager {
    fun initTheme(context: Context?) {
        AppCompatDelegate.setDefaultNightMode(if (isDarkMode(context)) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun isDarkMode(context: Context?): Boolean {
        return SharePrefUtils.getBoolean(context, "APP_MODE", true)
    }

    fun setThem(context: Context?, isDark: Boolean) {
        SharePrefUtils.putBoolean(context, "APP_MODE", isDark)
        AppCompatDelegate.setDefaultNightMode(if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}
