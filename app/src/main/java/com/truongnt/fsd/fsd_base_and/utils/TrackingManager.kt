//package com.ezt.ai.story.maker.utils
//
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import androidx.core.os.bundleOf
//import com.ezt.ai.story.maker.features.UserOption.CategoryInterestAct
//import com.ezt.ai.story.maker.features.create.CreateFrm
//import com.ezt.ai.story.maker.features.history.HistoryFrm
//import com.ezt.ai.story.maker.features.home.HomeFrm
//import com.ezt.ai.story.maker.features.ideasDetail.CategoryIdeaAct
//import com.ezt.ai.story.maker.features.languageStart.LanguageStartActivity
//import com.ezt.ai.story.maker.features.onboarding.OnbAct
//import com.ezt.ai.story.maker.features.quickplay.QuickPlayAct
//import com.ezt.ai.story.maker.features.setting.SettingFrm
//import com.ezt.ai.story.maker.features.splash.SplashAct
//import com.ezt.ai.story.maker.features.store.StoreFrm
//import com.ezt.ai.story.maker.features.story.StoryAct
//import com.google.firebase.analytics.FirebaseAnalytics
//import com.truongnt.fsd.fsd_base_and.utils.AppPreferences
//import kotlin.jvm.java
//import kotlin.to
//
//object TrackingManager {
//
//    private val TAG = "TrackingManager"
//
//    val PRE_SCREEN_KEY = "PRE_SCREEN_KEY"
//    val AD_DURATION_KEY = "AD_DURATION_KEY"
//    val PRE_SCREEN_DURATION_KEY = "PRE_SCREEN_DURATION_KEY"
//
//    private var firebaseAnalytics : FirebaseAnalytics? = null
//    lateinit var applicationContext: Context
//
//    fun init(context: Context) {
//        applicationContext = context
//        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
//    }
//
//    var currentScreen: String = ""
//    var screenIndex: Int = 0
//    var totalInterCount: Int = 0
//    var totalNativeCount: Int = 0
//    var totalRewardCount: Int = 0
//    var adLtv: Float = 0f
//
//    fun setUserProperties() {
//        currentScreen = AppPreferences.nameScreen
//        screenIndex = AppPreferences.getScreenIndex(applicationContext)
//        totalInterCount = AppPreferences.getInterCount(applicationContext)
//        totalNativeCount = AppPreferences.getNativeCount(applicationContext)
//        totalRewardCount = AppPreferences.getRewardCount(applicationContext)
//        adLtv = AppPreferences.getAdLtv(applicationContext)
//
//        firebaseAnalytics?.setUserProperty("current_screen", currentScreen)
//        firebaseAnalytics?.setUserProperty("screen_index_n", screenIndex.toString())
//        firebaseAnalytics?.setUserProperty(
//            "total_inter_count_n",
//            totalInterCount.toString()
//        )
//        firebaseAnalytics?.setUserProperty(
//            "total_reward_count_n",
//            totalRewardCount.toString()
//        )
//        firebaseAnalytics?.setUserProperty("ad_ltv_n", adLtv.toString())
//        Log.d(
//            TAG, "\nsetUserProperties: \n" +
//                    "current_screen: $currentScreen\n" +
//                    "screen_index_n: $screenIndex\n" +
//                    "total_inter_count_n: $totalInterCount\n" +
//                    "total_reward_count_n: $totalRewardCount\n" +
//                    "ad_ltv_n: $adLtv\n"
//        )
//    }
//
//    fun logEvent(name: String, bundle: Bundle?) {
//        setUserProperties()
//        firebaseAnalytics?.logEvent(name, bundle)
//        Log.d(TAG, "eventName: $name -- bundle: $bundle")
//    }
//
//    fun logEventScreenGo(
//
//        prevScreen: String,
//        screenName: String,
//        adDuration: Long,
//        prevScreenDuration: Long,
//    ) {
//        logEvent(
//            "screen_go", bundleOf(
//                "prev_screen" to prevScreen,
//                "screen_name" to screenName,
//                "ad_duration" to adDuration,
//                "prev_screen_duration" to prevScreenDuration
//            )
//        )
//    }
//
//    fun logEventScreenExit(
//
//        prevScreen: String,
//        prevScreenDuration: Long,
//    ) {
//        logEvent(
//            "screen_exit", bundleOf(
//                "prev_screen" to prevScreen,
//                "prev_screen_duration" to prevScreenDuration
//            )
//        )
//    }
//
//    fun logEventScreenReopen(
//
//        prevScreen: String,
//    ) {
//        logEvent(
//            "screen_reopen", bundleOf(
//                "screen_name" to prevScreen,
//            )
//        )
//    }
//
//    fun logEventElementShow(
//
//        elementName: String,
//        screenName: String,
//    ) {
//        logEvent(
//
//            "element_show", bundleOf(
//                "element_name" to elementName,
//                "screen_name" to screenName
//            )
//        )
//    }
//
//    fun logEventIapShow(
//
//        placement: String,
//        iapType: String,
//        isPremiumSale: Boolean,
//        isLifeTime: Boolean = false,
//    ) {
//        val packName: String = when (iapType) {
//            "premium" -> {
//                if (isLifeTime) {
//                    "premium_lifetime_24.99"
//                } else {
//                    if (isPremiumSale) {
//                        "premium_weeklyly_19.99_decrease_20,premium_monthly_19.99_decrease_20,premium_yearly_19.99_decrease_20,premium_lifetime_24.99"
//                    } else {
//                        "premium_weekly_2.49,premium_monthly_4.99,premium_yearly_19.99,premium_lifetime_24.99"
//                    }
//                }
//            }
//
//            else -> {
//                "remove_ads_offline_weekly_40,remove_ads_offline_monthly_40,remove_ads_07.07.25_increase_40"
//            }
//        }
//        logEvent(
//
//            "iap_show", bundleOf(
//                "placement" to placement,
//                "iap_type" to iapType,
//                "pack_name" to packName
//            )
//        )
//    }
//
//    fun logEventIapClick(
//
//        placement: String,
//        iapType: String,
//        packName: String,
//    ) {
//        logEvent(
//
//            "iap_click", bundleOf(
//                "placement" to placement,
//                "iap_type" to iapType,
//                "pack_name" to packName
//            )
//        )
//    }
//
//    fun logEventIapPurchase(
//
//        placement: String,
//        iapType: String,
//        packName: String,
//        price: Long,
//        currency: String,
//    ) {
//        logEvent(
//
//            "iap_purchase", bundleOf(
//                "placement" to placement,
//                "iap_type" to iapType,
//                "pack_name" to packName,
//                "price" to price,
//                "currency" to currency,
//            )
//        )
//    }
//
//    fun logEventAdImpression(
//        adFormat: String,
//        adMediationNetwork: String?,
//        adUnitId: String?,
//        placement: String,
//        value: Double,
//    ) {
//        val monetization = when (adMediationNetwork) {
//            "com.jirbo.adcolony.AdColonyAdapter", "com.google.ads.mediation.adcolony.AdColonyMediationAdapter" -> "adcolony"
//            "com.google.ads.mediation.applovin.ApplovinAdapter", "com.google.ads.mediation.applovin.AppLovinMediationAdapter" -> "applovin"
//            "com.google.ads.mediation.fyber.FyberMediationAdapter" -> "fyber"
//            "com.google.ads.mediation.inmobi.InMobiAdapter" -> "inmobi"
//            "com.google.ads.mediation.ironsource.IronSourceAdapter", "com.google.ads.mediation.ironsource.IronSourceRewardedAdapter" -> "ironsource"
//            "com.vungle.mediation.VungleInterstitialAdapter", "com.vungle.mediation.VungleAdapter" -> "vungle"
//            "com.google.ads.mediation.facebook.FacebookAdapter", "com.google.ads.mediation.facebook.FacebookMediationAdapter" -> "facebook"
//            "com.mbridge.msdk", "com.google.ads.mediation.mintegral.MintegralMediationAdapter" -> "mintegral"
//            "com.pangle.ads", "com.google.ads.mediation.pangle.PangleMediationAdapter" -> "pangle"
//            "com.google.ads.mediation.unity.UnityAdapter", "com.google.ads.mediation.unity.UnityMediationAdapter" -> "unity"
//            else -> "admob"
//        }
//        logEvent(
//
//            "ad_impression_2", bundleOf(
//                "ad_format" to adFormat,
//                "ad_platform" to "Admob",
//                "ad_network" to monetization,
//                "ad_unit_id" to adUnitId,
//                "placement" to placement,
//                "value" to value
//            )
//        )
//    }
//
//    fun logEventAdClick(
//
//        adFormat: String,
//        adMediationNetwork: String?,
//        adUnitId: String?,
//        placement: String,
//    ) {
//        val monetization = when (adMediationNetwork) {
//            "com.jirbo.adcolony.AdColonyAdapter", "com.google.ads.mediation.adcolony.AdColonyMediationAdapter" -> "adcolony"
//            "com.google.ads.mediation.applovin.ApplovinAdapter", "com.google.ads.mediation.applovin.AppLovinMediationAdapter" -> "applovin"
//            "com.google.ads.mediation.fyber.FyberMediationAdapter" -> "fyber"
//            "com.google.ads.mediation.inmobi.InMobiAdapter" -> "inmobi"
//            "com.google.ads.mediation.ironsource.IronSourceAdapter", "com.google.ads.mediation.ironsource.IronSourceRewardedAdapter" -> "ironsource"
//            "com.vungle.mediation.VungleInterstitialAdapter", "com.vungle.mediation.VungleAdapter" -> "vungle"
//            "com.google.ads.mediation.facebook.FacebookAdapter", "com.google.ads.mediation.facebook.FacebookMediationAdapter" -> "facebook"
//            "com.mbridge.msdk", "com.google.ads.mediation.mintegral.MintegralMediationAdapter" -> "mintegral"
//            "com.pangle.ads", "com.google.ads.mediation.pangle.PangleMediationAdapter" -> "pangle"
//            "com.google.ads.mediation.unity.UnityAdapter", "com.google.ads.mediation.unity.UnityMediationAdapter" -> "unity"
//            else -> "admob"
//        }
//        logEvent(
//
//            "ad_click", bundleOf(
//                "ad_format" to adFormat,
//                "ad_platform" to "Admob",
//                "ad_network" to monetization,
//                "ad_unit_id" to adUnitId,
//                "placement" to placement,
//            )
//        )
//    }
//
//    fun logEventButtonClick(
//
//        buttonName: String,
//        screenName: String,
//    ) {
//        logEvent(
//
//            "button_click", bundleOf(
//                "button_name" to buttonName,
//                "screen_name" to screenName
//            )
//        )
//    }
//
//    fun mappingScreenName(data: String): String {
//        return when (data) {
//            SplashAct::class.java.simpleName -> "splash"
//            LanguageStartActivity::class.java.simpleName -> "language"
//            OnbAct::class.java.simpleName -> "onboarding1"
//            HomeFrm::class.java.simpleName -> "home"
//            CreateFrm::class.java.simpleName -> "create_custome"
//            HistoryFrm::class.java.simpleName -> "history"
//            SettingFrm::class.java.simpleName -> "setting"
//            StoreFrm::class.java.simpleName -> "store"
//            CategoryInterestAct::class.java.simpleName -> "category_interest"
//            CategoryIdeaAct::class.java.simpleName -> "idea_detail_screen"
//            QuickPlayAct::class.java.simpleName -> "create_buy_idea"
//            StoryAct::class.java.simpleName -> "story"
//            else -> "none"
//        }
//    }
//}
