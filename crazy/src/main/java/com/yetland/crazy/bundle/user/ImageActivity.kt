package com.yetland.crazy.bundle.user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yetland.crazy.core.utils.LogUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    var urlList = ArrayList<String>()
    lateinit var adapter: ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val urls = intent.getStringExtra("url")

        urls.split(";")
                .filter { it.isNotEmpty() }
                .forEach { urlList.add(it) }

        LogUtils.e("urls = $urls")
        LogUtils.e("urlList = $urlList")
        adapter = ImageAdapter(this, urlList)
        viewPager.adapter = adapter
    }
}
