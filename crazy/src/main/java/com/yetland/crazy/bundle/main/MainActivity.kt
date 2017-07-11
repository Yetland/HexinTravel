package com.yetland.crazy.bundle.main

import android.os.Bundle
import android.util.Log
import com.yetland.crazy.bundle.destination.bean.Footer
import com.yetland.crazy.core.base.*
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.ynchinamobile.hexinlvxing.R

class MainActivity : BaseActivity(), MainContract.View {

    override fun getActivities(skip: Int) {

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

        rvList.recyclerViewListener = (object : RecyclerViewListener {
            override fun onRefresh() {
                currentPage = 0
                mainPresent.getActivities(currentPage)
            }

            override fun onLoadMore() {
                currentPage++
                Log.e("MainActivity", "onLoadMore")
                rvList.adapter.mList.add(Footer())
                rvList.adapter.notifyDataSetChanged()
                mainPresent.getActivities(currentPage)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList = findViewById(R.id.rv_list)
        onLoading("Loading")
        mainPresent.getActivities(currentPage)
    }
}
