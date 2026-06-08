package com.truongnt.fsd.fsd_base_and

import com.truongnt.fsd.nttads.AdsApplication

class MyApplication : AdsApplication() {
    override val adJustToken: String
        get() = ""
    override val debugMode: Boolean
        get() = true

    override fun onCreate() {
        super.onCreate()

    }

}