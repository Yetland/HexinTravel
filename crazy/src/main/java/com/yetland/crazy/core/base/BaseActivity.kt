package com.yetland.crazy.core.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.yetland.crazy.core.utils.ActivityManager

/**
 * @Name:           BaseActivity
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BaseActivity : AppCompatActivity() {
    var currentPage = 0
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