package com.truongnt.fsd.fsd_base_and.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;

public class EventTrackingHelper {
    public static String F1 = "f1_enter_splash";
    public static String F2 = "f2_enter_language";
    public static String F3 = "f3_enter_onboading_1";
    public static String F4 = "f4_enter_onboading_2";
    public static String F5 = "f5_enter_onboading_3";
    public static String F6 = "f6_enter_onboading_4";
    public static String F7 = "f7_enter_choose_cate_sceen";
    public static String F8 = "f8_enter_sub_screen";
    public static String F9 = "f9_enter_home";
    public static String F10 = "f10_enter_prepare_gen_story";
    public static String F11 = "f11_click_gen_story";
    public static String F12 = "f12_enter_chat_story";
    public static String F13 = "f13_chosse_action";


    public static void logEvent(Context context, String eventName) {
//        if (true) {
//            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            Bundle bundle = new Bundle();
//            firebaseAnalytics.logEvent(eventName, bundle);
//        } else Log.e("Firebase_even_tracking", eventName);
    }

    public static void logEventWithParam(Context context, String eventName, String param, String value) {
//        if (!BuildConfig.DEBUG) {
//            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            Bundle bundle = new Bundle();
//            bundle.putString(param, value);
//            firebaseAnalytics.logEvent(eventName, bundle);
//        } else Log.e("Firebase_even_tracking", eventName);
    }

    public static void logEventWithParam(Context context, String eventName, Bundle bundle) {
//        if (!BuildConfig.DEBUG) {
//            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            firebaseAnalytics.logEvent(eventName, bundle);
//        } else Log.e("Firebase_even_tracking", eventName);
    }



    public static void logEventWithMultipleParam(Context context, String eventName, HashMap<String, String> params) {
//        if (!BuildConfig.DEBUG) {
//            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            Bundle bundle = new Bundle();
//            for (String key : params.keySet()) {
//                bundle.putString(key, params.get(key));
//            }
//            firebaseAnalytics.logEvent(eventName, bundle);
//        } else Log.e("Firebase_even_tracking", eventName);
    }

    public static void logOneTimeEvent(Context context, String event) {
//        if (!BuildConfig.DEBUG) {
//            if (!SharePrefUtils.getBoolean(context, event, false)) {
//                logEvent(context, event);
//                SharePrefUtils.putBoolean(context, event, true);
//            }
//        }
    }

    public static void logUserPropertiesString(Context context, String key, String value) {
//        if (!BuildConfig.DEBUG) {
//            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            firebaseAnalytics.setUserProperty(key, value);
//        }
    }

    public static void logUserPropertiesInt(Context context, String key, int value) {
//        if (!BuildConfig.DEBUG) {
//            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            firebaseAnalytics.setUserProperty(key, String.valueOf(value));
//        }
    }

    public static void logUserPropertiesDouble(Context context, String key, Double value) {
//        if (!BuildConfig.DEBUG) {
//            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            firebaseAnalytics.setUserProperty(key, String.valueOf(value));
//        }
    }
}
