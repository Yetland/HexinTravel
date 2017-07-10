package com.yetland.crazy.core.utils

import android.app.Activity
import java.util.*

/**
 * @Name:           ActivityManager
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

class ActivityManager {

    /**
     * 在集合中记录打开的activity
     * @param activity
     */
    fun addActivity(activity: Activity) {
        allActivity.add(activity)
    }

    /**
     * 清楚所有的activity

     * @param activity
     */
    fun removeActivity(activity: Activity) {
        allActivity.remove(activity)
    }

    val activitySize: Int
        get() = allActivity.size

    /**
     * 清楚所有activity
     */
    fun destroyAllActivity() {
        for (i in allActivity.indices) {
            allActivity[i].finish()
        }
        allActivity.clear()
    }

    fun containActivity(activity: Activity): Boolean {
        return allActivity.contains(activity)
    }

    companion object {
        val instance = ActivityManager()
        /** 管理所有ui对象。  */
        private val allActivity = ArrayList<Activity>()
    }
}
