package com.truongnt.fsd.fsd_base_and.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.truongnt.fsd.fsd_base_and.BuildConfig
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object EncryptionUtil {
    private const val payLoadKey = "a45dfdf33732be57327a745e2b5f57ba"
    private const val Key = "abcdfhjagdshj@12"

    @SuppressLint("HardwareIds")
    fun getDeviceID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getBase64DecryptConfig(config: String): String? {
        val rawConfig: String
        try {
            rawConfig = decrypt(config)
            return android.util.Base64.encodeToString(
                rawConfig.toByteArray(),
                android.util.Base64.DEFAULT
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }



    fun encrypt(context: Context): String {

        val data = DeviceData()

        Log.e("ajkshdfjkhsaf", "encrypt: " + getDeviceID(context))

        try {
            data.apply {
                client_id = getDeviceID(context)
                name = Build.MODEL
                type = "1"
                version = BuildConfig.VERSION_NAME
                package_id = BuildConfig.APPLICATION_ID
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = ByteArray(16)
        val ivParams = IvParameterSpec(iv)
        val secretKey = SecretKeySpec(payLoadKey.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams)
        val encrypted = cipher.doFinal(Gson().toJson(data).toByteArray())
        val combined = ByteArray(iv.size + encrypted.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(encrypted, 0, combined, iv.size, encrypted.size)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(combined)
        } else android.util.Base64.encodeToString(combined, android.util.Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun decrypt(encoded: String): String {
        var combined = ByteArray(0)
        combined = android.util.Base64.decode(encoded, android.util.Base64.DEFAULT)
        val iv = ByteArray(16)
        val encrypted = ByteArray(combined.size - iv.size)
        System.arraycopy(combined, 0, iv, 0, iv.size)
        System.arraycopy(combined, iv.size, encrypted, 0, encrypted.size)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivParams = IvParameterSpec(iv)
        val secretKey = SecretKeySpec(payLoadKey.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams)
        val decrypted = cipher.doFinal(encrypted)
        return String(decrypted)
    }
}

data class DeviceData(
    @SerializedName("device_id")
    var device_id: Int = 0,

    @SerializedName("client_id")
    var client_id: String = "",

    @SerializedName("type")
    var type: String = "1",

    @SerializedName("name")
    var name: String = "ABCD",

    @SerializedName("ip")
    var ip: String = "",

    @SerializedName("version")
    var version: String = "",

    @SerializedName("package_id")
    var package_id: String = ""
)