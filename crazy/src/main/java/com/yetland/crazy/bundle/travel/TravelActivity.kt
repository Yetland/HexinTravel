package com.yetland.crazy.bundle.travel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.ynchinamobile.hexinlvxing.R
import com.yetland.crazy.bundle.travel.adapter.TravelAdapter

class TravelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)
        val rvTravel = findViewById<RecyclerView>(R.id.rv_travel)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayout.VERTICAL
        val data = ArrayList<String>()
        val s: String = "HELLO WORLD"
        for (x in s) {
            data.add(x.toString())
        }
        val adapter = TravelAdapter<String>(this)

        Log.e("TravelActivity", data.toString())
        adapter.mList = data

        rvTravel.layoutManager = layoutManager
        rvTravel.adapter = adapter

    }
}
