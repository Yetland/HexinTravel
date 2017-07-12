package com.yetland.crazy.bundle.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.yetland.crazy.bundle.destination.bean.Footer
import com.yetland.crazy.bundle.main.contract.MainContract
import com.yetland.crazy.bundle.main.contract.MainModel
import com.yetland.crazy.bundle.main.contract.MainPresent
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.ynchinamobile.hexinlvxing.R

class MainActivity : BaseActivity(), MainContract.View, RecyclerViewListener {

    override fun onRefresh() {
        currentPage = 0
        getActivities(currentPage)
    }

    override fun onLoadMore() {
        currentPage++
        Log.e("MainActivity", "onLoadMore")
        rvList.adapter.mList.add(Footer())
        rvList.adapter.notifyDataSetChanged()
        getActivities(currentPage)
    }

    override fun onErrorClick() {
        onLoading("Loading")
        currentPage = 0
        getActivities(currentPage)
    }

    override fun getActivities(skip: Int) {
        mainPresent.getActivities(currentPage)
    }

    var mainModel = MainModel()
    var mainPresent = MainPresent(mainModel, this)
    var list = ArrayList<BaseEntity>()

    lateinit var rvList: BaseRecyclerView

    override fun onLoading(msg: String) {
        rvList.onLoading()
    }

    override fun onError(msg: String) {
        rvList.onLoadError(msg)
    }

    override fun onComplete(activityModel: Data<ActivityInfo>) {

        Log.e("MainActivity", "onComplete")

        val results = activityModel.results!!

        if (results.size == 0) {
            if (currentPage == 0) {
                list = ArrayList()
                val footer = Footer()
                footer.noMore = true
                list.add(footer)
                rvList.onComplete(list, true)
            } else {
                list.removeAt(list.size - 1)
                val footer = Footer()
                footer.noMore = true
                list.add(footer)
                rvList.onComplete(list, true)
            }
        } else {
            if (currentPage == 0) {
                list = ArrayList<BaseEntity>()
            } else {
                list.removeAt(list.size - 1)
            }
            list.addAll(results)
            rvList.onComplete(list)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList = findViewById(R.id.rv_list)
        rvList.recyclerViewListener = this
        onLoading("Loading")
        getActivities(currentPage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
