package com.ynchinamobile.hexinlvxing.core.app

import android.app.Application
import android.content.Context

/**
 * @Name:           HexinApplication
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class HexinApplication : Application() {

    var context: Context? = null
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}