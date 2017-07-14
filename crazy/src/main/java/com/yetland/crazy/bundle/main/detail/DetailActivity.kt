package com.yetland.crazy.bundle.main.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.yetland.crazy.bundle.destination.bean.Footer
import com.yetland.crazy.bundle.main.contract.ActivityDetailContract
import com.yetland.crazy.bundle.main.contract.ActivityDetailModel
import com.yetland.crazy.bundle.main.contract.ActivityDetailPresenter
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.entity.*
import com.ynchinamobile.hexinlvxing.R

class DetailActivity : BaseActivity(), ActivityDetailContract.View,RecyclerViewListener {
    override fun onRefresh() {
        currentPage = 0
        getComment(point, currentPage)
    }

    override fun onLoadMore() {
        currentPage++
        Log.e("MainActivity", "onLoadMore")
        rvDetailList.adapter.mList.add(Footer())
        rvDetailList.adapter.notifyDataSetChanged()
        getComment(point, currentPage)
    }

    override fun onErrorClick() {
        currentPage = 0
        rvDetailList.onLoading()
        getComment(point, currentPage)
    }

    override fun getComment(activityPointer: Point, page: Int) {
        presenter.getComment(activityPointer, page)
    }

    override fun failed(msg: String) {
        rvDetailList.onLoadError(msg)
    }

    override fun getCommentSuccess(data: Data<Comment>) {

        Log.e("DetailActivity", "getCommentSuccess")

        val results = data.results

        if (results == null || results.size == 0) {
            if (currentPage == 0) {
                list = ArrayList()
                val footer = Footer()
                footer.noMore = true
                list.add(activityInfo)
                list.add(footer)
                rvDetailList.onComplete(list, true)
            } else {
                list.removeAt(list.size - 1)
                val footer = Footer()
                footer.noMore = true
                list.add(footer)
                rvDetailList.onComplete(list, true)
            }
        } else {
            if (currentPage == 0) {
                list = ArrayList<BaseEntity>()
                list.add(activityInfo)
            } else {
                list.removeAt(list.size - 1)
            }
            list.addAll(results)
            rvDetailList.onComplete(list)
        }
    }

    val TAG = "DetailActivity"
    var list = ArrayList<BaseEntity>()
    var model = ActivityDetailModel()
    var presenter = ActivityDetailPresenter(model, this)
    lateinit var point: Point
    lateinit var rvDetailList: BaseRecyclerView
    lateinit var activityInfo: ActivityInfo
    var holderPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = activity.intent.extras
        activityInfo = bundle.getSerializable("activityInfo") as ActivityInfo
        holderPosition = bundle.getInt("position")
        activityInfo.clickable = false
        rvDetailList = findViewById(R.id.rv_activity_detail)
        rvDetailList.initView(this)
        rvDetailList.recyclerViewListener = this
        rvDetailList.onLoading()
        point = Point("Activity", activityInfo.objectId.toString())
        Log.e("DetailActivity", "name : ${point.className}, id = ${point.objectId}")
        getComment(point, 0)
    }

    override fun onBackPressed() {
        Log.e("DetailActivity", "onBackPressed")

        val bundle = Bundle()
        if (rvDetailList.adapter.mList.size > 0) {
            val activityInfo = rvDetailList.adapter.mList[0]
            activityInfo.clickable = true
            bundle.putSerializable("activityInfo", activityInfo)
            bundle.putInt("position", holderPosition)
            setResult(IntentResultCode.MAIN_TO_DETAIL_RESULT, Intent().putExtras(bundle))
        }
        super.onBackPressed()
    }
}
