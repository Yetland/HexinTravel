package com.ynchinamobile.hexinlvxing.bundle.destination

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.ynchinamobile.hexinlvxing.R
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.Car
import com.ynchinamobile.hexinlvxing.core.base.BaseMultiTypeAdapter
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.Dog
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.Visitable

class DestinationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)

        val rvList = findViewById<RecyclerView>(R.id.rv_destination)
        val list: ArrayList<Visitable> = ArrayList()
        val dog: Dog = Dog("Dog1")
        val car: Car = Car("Car1")
        list.add(dog)
        list.add(car)
        list.add(dog)
        list.add(car)
        list.add(car)
        list.add(dog)
        list.add(dog)
        list.add(dog)
        list.add(car)
        list.add(dog)

        val adapter = BaseMultiTypeAdapter()
        adapter.mList = list
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayout.VERTICAL
        rvList.layoutManager = layoutManager
        rvList.adapter = adapter
    }
}
