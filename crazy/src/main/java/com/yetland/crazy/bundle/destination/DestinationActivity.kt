package com.yetland.crazy.bundle.destination

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.ynchinamobile.hexinlvxing.R
import com.yetland.crazy.core.base.BaseMultiTypeAdapter
import com.yetland.crazy.core.entity.ActivityInfo

class DestinationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)

        val rvList = findViewById<RecyclerView>(R.id.rv_destination)
        val list: ArrayList<ActivityInfo> = ArrayList()

        val adapter = BaseMultiTypeAdapter()
//        adapter.mList = list
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayout.VERTICAL
        rvList.layoutManager = layoutManager
        rvList.adapter = adapter
    }
}
