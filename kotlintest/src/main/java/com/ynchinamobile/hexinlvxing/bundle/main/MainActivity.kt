package com.ynchinamobile.hexinlvxing.bundle.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.ynchinamobile.hexinlvxing.R
import com.ynchinamobile.hexinlvxing.bundle.destination.DestinationActivity
import com.ynchinamobile.hexinlvxing.bundle.main.adapter.MainListAdapter
import com.ynchinamobile.hexinlvxing.bundle.travel.TravelActivity
import com.ynchinamobile.hexinlvxing.core.base.BaseActivity
import com.ynchinamobile.hexinlvxing.core.base.OnRecyclerViewItemClickListener
import com.ynchinamobile.hexinlvxing.core.entity.ActivityInfo
import com.ynchinamobile.hexinlvxing.core.entity.Data
import com.ynchinamobile.hexinlvxing.core.utils.makeShortToast

class MainActivity : BaseActivity(), MainContract.View {

    var mainPresent = MainPresent()
    var mainModel = MainModel()

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
                val intent = Intent(this@MainActivity,DestinationActivity::class.java)
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
        mainPresent.setVM(this, mainModel)

        rvList = findViewById(R.id.rv_list)
        pgLoading = findViewById(R.id.pg_loading)
        tvError = findViewById(R.id.tv_error)

        onLoading("Loading")
        mainPresent.getActivityModel()
    }
}
