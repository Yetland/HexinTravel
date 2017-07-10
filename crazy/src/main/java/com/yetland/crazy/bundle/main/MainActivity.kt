package com.yetland.crazy.bundle.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.yetland.crazy.bundle.destination.DestinationActivity
import com.yetland.crazy.bundle.main.adapter.MainListAdapter
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.OnRecyclerViewItemClickListener
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.utils.makeShortToast
import com.ynchinamobile.hexinlvxing.R

class MainActivity : BaseActivity(), MainContract.View {

    var mainModel = MainModel()
    var mainPresent = MainPresent(mainModel, this)

    lateinit var pgLoading: ProgressBar
    lateinit var tvError: TextView
    lateinit var rvList: RecyclerView

    override fun onLoading(msg: String) {
        makeShortToast(this, "loading")
        pgLoading.visibility = View.VISIBLE
        tvError.visibility = View.VISIBLE
        rvList.visibility = View.GONE
    }

    override fun onError(msg: String) {
        pgLoading.visibility = View.GONE
        tvError.visibility = View.VISIBLE
        tvError.text = msg
        rvList.visibility = View.GONE
    }

    override fun onComplete(activityModel: Data<ActivityInfo>) {

        pgLoading.visibility = View.GONE
        tvError.visibility = View.GONE
        rvList.visibility = View.VISIBLE
        val results: ArrayList<ActivityInfo> = activityModel.results!!

        val adapter: MainListAdapter = MainListAdapter()
        adapter.mList = results

        adapter.onItemClickListener = (object : OnRecyclerViewItemClickListener {
            override fun onRecyclerViewItemClick(position: Int) {
                makeShortToast(activity, "title = ${results[position].title} , content =${results[position].content}")
                val intent = Intent(this@MainActivity, DestinationActivity::class.java)
                startActivity(intent)
            }
        })

        val layoutManager: LinearLayoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rvList.layoutManager = layoutManager
        rvList.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList = findViewById(R.id.rv_list)
        pgLoading = findViewById(R.id.pg_loading)
        tvError = findViewById(R.id.tv_error)

        onLoading("Loading")
        mainPresent.getActivityModel()
    }
}
