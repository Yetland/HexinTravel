package com.yetland.crazy.core.app

import android.app.Application
import android.content.Context
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.Utils

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
        Utils.init(applicationContext)
        LogUtils.Builder().setLog2FileSwitch(true)
    }
}