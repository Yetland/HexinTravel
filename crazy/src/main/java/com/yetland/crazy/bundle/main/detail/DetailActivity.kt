package com.yetland.crazy.bundle.main.detail

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.yetland.crazy.bundle.main.contract.ActivityDetailContract
import com.yetland.crazy.bundle.main.contract.ActivityDetailModel
import com.yetland.crazy.bundle.main.contract.ActivityDetailPresenter
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.SharedPrefrenceUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R

class DetailActivity : BaseActivity(), ActivityDetailContract.View, RecyclerViewListener {

    var list = ArrayList<BaseEntity>()
    var model = ActivityDetailModel()
    var presenter = ActivityDetailPresenter(model, this)
    var map = HashMap<String, String>()
    lateinit var rvDetailList: BaseRecyclerView
    lateinit var activityInfo: ActivityInfo
    var holderPosition = 0
    lateinit var user: _User
    lateinit var fabEdit: FloatingActionButton
    lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = activity.intent.extras
        activityInfo = bundle.getSerializable("activityInfo") as ActivityInfo
        holderPosition = bundle.getInt("position")

        fabEdit = findViewById<FloatingActionButton>(R.id.fab_edit)
        rvDetailList = findViewById(R.id.rv_activity_detail)
        rvDetailList.initView(this)
        rvDetailList.recyclerViewListener = this
        rvDetailList.onLoading()

        map.put("activityId", activityInfo.objectId)
        getComment(map, 0)

        user = SharedPrefrenceUtils.getUserInfo(activity)
        fabEdit.setOnClickListener({
            if (user.objectId.isEmpty() || user.objectId.isEmpty()) {
                ToastUtils.showShortSafe("Please login")
            } else {
                val dialog = MaterialDialog.Builder(activity)
                dialog.title("评论")
                        .inputRange(1,150)
                        .input("Write something", null,false, {
                            d, input ->
                            if (input.isEmpty()) {
                                ToastUtils.showShortSafe("Empty content")
                            } else {
                                d.dismiss()
                                val activityPoint = Point("Pointer", "Activity", activityInfo.objectId)
                                val userPoint = Point("Pointer", "_User", user.objectId)
                                val comment = CommitComment(activityPoint, userPoint, input.toString())

                                val c = Comment()
                                c.creator = user
                                c.activity = activityInfo
                                c.content = input.toString()
                                c.activityId = activityInfo.objectId
                                c.creatorId = user.objectId
                                LogUtils.e(Gson().toJson(c))
                                writeComment(comment)
                            }
                        })
                        .positiveText("评论")
                        .negativeText("取消")
                        .show()
            }
        })
    }

    override fun onRefresh() {
        currentPage = 0
        getComment(map, currentPage)
    }

    override fun onLoadMore() {
        currentPage++
        LogUtils.e("onLoadMore")
        getComment(map, currentPage)
    }

    override fun onErrorClick() {
        currentPage = 0
        rvDetailList.onLoading()
        getComment(map, currentPage)
    }

    override fun getComment(map: HashMap<String, String>, page: Int) {
        presenter.getComment(map, page)
    }

    override fun failed(msg: String) {
        dialog.dismiss()
        rvDetailList.onLoadError(msg)
    }

    override fun getCommentSuccess(data: Data<Comment>) {

        LogUtils.e("getCommentSuccess")

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
            activityInfo = results[0].activity
            activityInfo.clickable = false
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

    override fun writeComment(comment: CommitComment) {
        dialog = ProgressDialog(activity)
        dialog.setMessage("LOADING...")
        dialog.show()
        presenter.writeComment(comment)
    }

    override fun writeCommentSuccess(result: BaseResult) {
        val map1 = HashMap<String, Any>()
        map1.put("commentCount", ++activityInfo.commentCount)
        updateActivity(activityInfo.objectId, Gson().toJson(map1))
    }

    override fun writeCommentFailed(msg: String) {
        dialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }

    override fun updateActivity(activityId: String, where: String) {
        LogUtils.e(where)
        presenter.updateActivity(activityId, where)
    }

    override fun updateActivitySuccess() {
        dialog.dismiss()
        rvDetailList.swipeRefreshLayout.isRefreshing = true
        currentPage = 0
        getComment(map, currentPage)
    }

    override fun updateActivityFailed(msg: String) {
        dialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }


    override fun onBackPressed() {
        LogUtils.e("onBackPressed")

        val bundle = Bundle()
        if (rvDetailList.adapter.mList.size > 0 && rvDetailList.adapter.mList[0] is ActivityInfo) {
            val activityInfo = rvDetailList.adapter.mList[0]
            bundle.putSerializable("activityInfo", activityInfo)
            bundle.putInt("position", holderPosition)
            setResult(IntentResultCode.MAIN_TO_DETAIL_RESULT, Intent().putExtras(bundle))
        }
        super.onBackPressed()
    }
}
