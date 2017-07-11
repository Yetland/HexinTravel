package com.yetland.crazy.bundle.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ynchinamobile.hexinlvxing.R
import com.yetland.crazy.core.base.BaseMultiTypeAdapter
import com.yetland.crazy.core.entity.BaseEntity

class UserDataActivity : AppCompatActivity() {

    val TAG = "UserDataActivity"
    var adapter = BaseMultiTypeAdapter()
    var list = ArrayList<BaseEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)
        /*val baseRecyclerView = findViewById <BaseRecyclerView>(R.id.baseRecyclerView)

        var i = 0
        val j = 10
        while (i < j) {
            if (i % 2 == 0) {
                list.add(Dog("Dog" + i))
            } else {
                list.add(Car("Car" + i))
            }
            i++
        }
        adapter.mList = list

        baseRecyclerView.adapter = adapter
        baseRecyclerView.recyclerView.adapter = adapter
        baseRecyclerView.recyclerViewListener = refreshListener
        baseRecyclerView.onComplete()*/
    }
/*
    var refreshListener = (object : RecyclerViewListener {

        override fun onRefresh() {
            Log.e(TAG, "onRefresh,list.size = ${list.size}")
            val i = list.size
            if (i % 2 == 0) {
                list.add(Dog("Dog" + i))
            } else {
                list.add(Car("Car" + i))
            }
            adapter.mList = list
            handler.sendEmptyMessageDelayed(1, 3000)
        }

        override fun onLoadMore() {
            Log.e(TAG, "onLoadMore,list.size = ${list.size}")
            list.add(Footer())
            adapter.mList = list
            adapter.notifyDataSetChanged()
            handler.sendEmptyMessageDelayed(2, 10000)
        }
    })

    @SuppressLint("HandlerLeak")
    var handler = (object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                ON_REFRESH_COMPLETE -> onRefreshComplete()
                ON_LOAD_MORE_COMPLETE -> onLoadMoreComplete()
            }
        }
    })

    fun onRefreshComplete() {
        baseRecyclerView.onComplete()
    }

    fun onLoadMoreComplete() {
        Log.e(TAG, "onLoadMoreComplete,list.size = ${list.size}")
        list.removeAt(list.size - 1)
        var i = list.size
        val j = i + 9
        while (i < j) {
            i++
            if (i % 2 == 0) {
                list.add(Dog("Dog" + i))
            } else {
                list.add(Car("Car" + i))
            }
        }
        adapter.mList = list
        baseRecyclerView.onComplete()
    }*/

    val ON_REFRESH_COMPLETE = 1
    val ON_LOAD_MORE_COMPLETE = 2
}
