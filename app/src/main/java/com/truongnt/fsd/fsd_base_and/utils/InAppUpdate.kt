//package com.ezt.ai.story.maker.utils
//
//import android.app.Activity
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.play.core.appupdate.AppUpdateInfo
//import com.google.android.play.core.appupdate.AppUpdateManager
//import com.google.android.play.core.appupdate.AppUpdateManagerFactory
//import com.google.android.play.core.appupdate.AppUpdateOptions
//import com.google.android.play.core.install.InstallState
//import com.google.android.play.core.install.InstallStateUpdatedListener
//import com.google.android.play.core.install.model.AppUpdateType
//import com.google.android.play.core.install.model.InstallStatus
//import com.google.android.play.core.install.model.UpdateAvailability
//
//class InAppUpdate(
//    activity: AppCompatActivity,
//    forceUpdate: Boolean,
//    private val installUpdatedListener: InstallUpdatedListener,
//) : InstallStateUpdatedListener {
//    private var appUpdateManager: AppUpdateManager
//    private val MY_REQUEST_CODE = 500
//    private var parentActivity: Activity = activity
//
//    private var currentType = AppUpdateType.FLEXIBLE
//
//    init {
//        appUpdateManager = AppUpdateManagerFactory.create(parentActivity)
//        if (forceUpdate) {
//            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
//                if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
//                    startUpdate(info, AppUpdateType.IMMEDIATE)
//                } else {
//                    installUpdatedListener.onUpdateNextAction()
//                }
//            }
//            appUpdateManager.appUpdateInfo.addOnFailureListener {
//                installUpdatedListener.onUpdateNextAction()
//            }
//            appUpdateManager.registerListener(this)
//        } else {
//            installUpdatedListener.onUpdateNextAction()
//        }
//    }
//
//    private fun startUpdate(info: AppUpdateInfo, type: Int) {
//        appUpdateManager.startUpdateFlowForResult(
//            info,
//            parentActivity,
//            AppUpdateOptions.newBuilder(type)
//                .setAllowAssetPackDeletion(false)
//                .build(),
//            MY_REQUEST_CODE
//        )
//        currentType = type
//    }
//
//    fun onResume() {
//        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
//            if (currentType == AppUpdateType.FLEXIBLE) {
//                if (info.installStatus() == InstallStatus.DOWNLOADED)
//                    flexibleUpdateDownloadCompleted()
//            } else if (currentType == AppUpdateType.IMMEDIATE) {
//                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
//                    startUpdate(info, AppUpdateType.IMMEDIATE)
//                }
//            }
//        }
//    }
//
//    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == MY_REQUEST_CODE) {
//            if (resultCode != AppCompatActivity.RESULT_OK) {
//                installUpdatedListener.onUpdateCancel()
//            }
//        }
//    }
//
//    private fun flexibleUpdateDownloadCompleted() {
//        installUpdatedListener.onUpdateNextAction()
//    }
//
//    fun onDestroy() {
//        appUpdateManager.unregisterListener(this)
//    }
//
//    override fun onStateUpdate(state: InstallState) {
//        if (state.installStatus() == InstallStatus.DOWNLOADED) {
//            flexibleUpdateDownloadCompleted()
//        }
//    }
//}
//
//interface InstallUpdatedListener {
//    fun onUpdateNextAction()
//    fun onUpdateCancel()
//}