package com.ynchinamobile.hexinlvxing.core.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ynchinamobile.hexinlvxing.core.utils.ActivityManager

/**
 * @Name:           BaseActivity
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BaseActivity : AppCompatActivity() {
    lateinit var activity: Activity

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        ActivityManager().addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager().removeActivity(this)
    }
}