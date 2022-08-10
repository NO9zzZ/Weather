package com.wyz.weather.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @author zzZ
 * @date 2022/7/21 11:25
 * @describe
 * @e-mail:646888521@qq.com
 */
object NetworkUtil {
    fun isNetworkAvailable(activity: Activity): Boolean {
        val context = activity.applicationContext
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager == null) {
            return false
        } else {
            val networkInfo = connectivityManager.allNetworkInfo
            if (networkInfo != null && networkInfo.size > 0) {
                for (i in networkInfo.indices) {
                    if (networkInfo[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }
}