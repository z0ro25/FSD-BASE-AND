package com.truongnt.fsd.fsd_base_and.features.onb

import androidx.viewpager2.widget.ViewPager2
import com.ezt.ai.story.maker.features.onboarding.adapters.OnbAdapter
import com.truongnt.fsd.fsd_base_and.Base.BaseActivity
import com.truongnt.fsd.fsd_base_and.R
import com.truongnt.fsd.fsd_base_and.databinding.ActOnbBinding
import com.truongnt.fsd.fsd_base_and.tool.sharePreferenceTool.SharePrefUtils
import com.truongnt.fsd.fsd_base_and.utils.RemoteConfigUtils
import com.truongnt.fsd.fsd_base_and.utils.ThemeManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnbAct : BaseActivity<ActOnbBinding>() {
    override val setViewBinding: ActOnbBinding
        get() = ActOnbBinding.inflate(layoutInflater)

    var listConTent = ArrayList<Triple<Int, Int, Int>>()
    val adapter by lazy { OnbAdapter(this, listConTent) }

    override fun initView() {

        binding.apply {
            val adsEnable = !RemoteConfigUtils.getRemoteConfigBoolean(
                this@OnbAct,
                RemoteConfigUtils.NATIVE_ONB_FULL
            )

            val isDarkmode = ThemeManager.isDarkMode(this@OnbAct)

            listConTent.add(
                Triple(
                    R.string.p1_title,
                    R.string.p1_content,
                    R.drawable.ic_vi
                )
            )


            listConTent.add(
                Triple(
                    R.string.p2_title,
                    R.string.p2_content,
                    R.drawable.ic_vi
                )
            )

            listConTent.add(
                Triple(
                    R.string.p3_title,
                    R.string.p3_content,
                    R.drawable.ic_vi
                )
            )


            vpOnb.adapter = adapter
            dotIndicator.attachTo(vpOnb)

            MainScope().launch {
                delay(200)

            }
        }
    }

    override fun viewListener() {
        binding.apply {

            vpOnb.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    val data = listConTent[position]

                    tvTitle.post {
                        tvTitle.text =
                            getString(if (data.first == 0) R.string.p1_title else data.first)
                    }

                    val isRemoveAds =
                        SharePrefUtils.isPremium(this@OnbAct) or SharePrefUtils.isSubscription(this@OnbAct) or SharePrefUtils.isTier1(
                            this@OnbAct
                        )
                }
            })

            tvContinue.setOnClickListener {
                if (vpOnb.currentItem < adapter.itemCount - 1) {
                    vpOnb.currentItem++
                } else {
//                    launchActivity<>(){}
                    finish()
                }
            }

            btnContinue.setOnClickListener {
                if (vpOnb.currentItem < adapter.itemCount - 1) {

                    vpOnb.currentItem++
                } else {
                    finish()
                }
            }

            icCloseNative.setOnClickListener {
                vpOnb.currentItem++
            }
        }
    }

    override fun dataObservable() {

    }
}