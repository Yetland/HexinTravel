package com.yetland.crazy.bundle.user

import android.content.Context
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.util.DisplayMetrics
import android.view.WindowManager
import com.yetland.crazy.core.base.BaseActivity
import com.ynchinamobile.hexinlvxing.R

class UserDetailActivity : BaseActivity() {

    lateinit var appbarLayout: AppBarLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        appbarLayout = findViewById(R.id.layout_appbar)

        val manager: WindowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        manager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = width * 9 / 16
        val layoutParams = AppBarLayout.LayoutParams(width, height)
        appbarLayout.layoutParams = layoutParams
    }
}
