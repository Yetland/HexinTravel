package com.ynchinamobile.hexinlvxing.core.utils

import android.content.Context
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager


/**
 * @Name:           NetUtil
 * @Author:         yeliang
 * @Date:           2017/7/5
 */

fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) {
        return false
    }
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    if (networkInfo != null) {
        return networkInfo.isAvailable
    }

    return false
}
