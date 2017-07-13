package com.yetland.crazy.bundle.welcome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.yetland.crazy.bundle.main.MainActivity
import com.yetland.crazy.core.base.BaseActivity
import com.ynchinamobile.hexinlvxing.R

class WelcomeActivity : BaseActivity() {

    val GO_HOME: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        handler.sendEmptyMessageDelayed(GO_HOME, 3000)
    }

    @SuppressLint("HandlerLeak")
    private var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val intent = Intent()
            when (msg?.what) {
                GO_HOME -> {
                    intent.setClass(activity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
