package com.yetland.crazy.bundle.main.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.yetland.crazy.bundle.main.contract.ActivityDetailContract
import com.yetland.crazy.bundle.main.contract.ActivityDetailModel
import com.yetland.crazy.bundle.main.contract.ActivityDetailPresenter
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.include_action_bar.*

class DetailActivity : BaseActivity(), ActivityDetailContract.View, RecyclerViewListener {


    var presenter = ActivityDetailPresenter(ActivityDetailModel(), this)

    var list = ArrayList<BaseEntity>()
    var map = HashMap<String, String>()
    lateinit var activityInfo: ActivityInfo
    lateinit var activityId: String
    var holderPosition = -1
    lateinit var user: _User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar2)
        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activityId = intent.getStringExtra("activityId")
        holderPosition = intent.getIntExtra("position", -1)

        user = SharedPreferencesUtils.getUserInfo()

        ivAction.setImageResource(R.mipmap.ic_delete)
        ivAction.visibility = View.GONE


        rvActivityDetail.initView(this)
        rvActivityDetail.recyclerViewListener = this
        rvActivityDetail.onLoading()

        fabEdit.visibility = View.GONE

        fabEdit.setOnClickListener({
            if (user.objectId.isEmpty() || user.objectId.isEmpty()) {
                ToastUtils.showShortSafe("Please login")
            } else {
                val dialog = MaterialDialog.Builder(activity)
                dialog.title("评论")
                        .inputRange(1, 150)
                        .input("Write something", null, false, {
                            d, input ->
                            if (input.isEmpty()) {
                                ToastUtils.showShortSafe("Empty content")
                            } else {
                                d.dismiss()
                                val activityPoint = Point("Activity", activityInfo.objectId)
                                val userPoint = Point("_User", user.objectId)
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

        getActivity(activityId)
    }

    override fun getActivity(activityId: String) {
        presenter.getActivity(activityId)
    }

    override fun getActivitySuccess(activityInfo: ActivityInfo) {
        this.activityInfo = activityInfo
        if (this.activityInfo.objectId.isNotEmpty()) {

            this.activityInfo.clickable = false
            if (isLogin && this.activityInfo.creator.objectId == currentLoginUser.objectId) {
                ivAction.visibility = View.VISIBLE
                ivAction.setOnClickListener({
                    progressDialog.show()
                    deleteActivity(this.activityInfo.objectId)
                })
            } else {
                ivAction.visibility = View.GONE
            }
            fabEdit.visibility = View.VISIBLE
            map.put("activityId", this.activityInfo.objectId)
            getComment(map, 0)
        } else {
            rvActivityDetail.isErrorClickable = false
            rvActivityDetail.onLoadError(getString(R.string.forward_deleted))
        }
    }

    override fun getActivityFailed(msg: String) {
        rvActivityDetail.isErrorClickable = false
        rvActivityDetail.onLoadError(getString(R.string.forward_deleted))
    }

    override fun onDataChanged() {
        rvActivityDetail.adapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        currentPage = 0
        getActivity(activityId)
    }

    override fun onLoadMore() {
        currentPage++
        LogUtils.e("onLoadMore")
        getComment(map, currentPage)
    }

    override fun onErrorClick() {
        currentPage = 0
        rvActivityDetail.onLoading()
        getActivity(activityId)
    }

    override fun getComment(map: HashMap<String, String>, page: Int) {
        presenter.getComment(map, page)
    }

    override fun failed(msg: String) {
        progressDialog.dismiss()
        rvActivityDetail.onLoadError(msg)
    }

    override fun getCommentSuccess(data: Data<Comment>) {

        LogUtils.e("getCommentSuccess")
        val list = ArrayList<BaseEntity>()
        list.addAll(data.results!!)
        rvActivityDetail.onCompleteWithHeader(list, currentPage, activityInfo)
    }

    override fun writeComment(comment: CommitComment) {
        progressDialog.show()
        presenter.writeComment(comment)
    }

    override fun writeCommentSuccess(result: BaseResult) {

        val map = HashMap<String, Any>()
        val op = Op()
        op.__op = "Increment"
        op.amount = 1
        map.put("commentCount", op)
        updateActivity(activityInfo.objectId, Gson().toJson(map))
    }

    override fun writeCommentFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }

    override fun updateActivity(activityId: String, where: String) {
        LogUtils.e(where)
        presenter.updateActivity(activityId, where)
    }

    override fun updateActivitySuccess() {
        progressDialog.dismiss()
        activityInfo.commentCount++
        rvActivityDetail.swipeRefreshLayout.isRefreshing = true
        currentPage = 0
        getComment(map, currentPage)
    }

    override fun updateActivityFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }


    override fun onBackPressed() {
        LogUtils.e("onBackPressed")

        val bundle = Bundle()
        if (holderPosition != -1 && rvActivityDetail.adapter.mList.size > 0 && rvActivityDetail.adapter.mList[0] is ActivityInfo) {
            val activityInfo = rvActivityDetail.adapter.mList[0]
            bundle.putSerializable("activityInfo", activityInfo)
            bundle.putInt("position", holderPosition)
            setResult(IntentResultCode.MAIN_TO_DETAIL_RESULT, Intent().putExtras(bundle))
        }
        super.onBackPressed()
    }

    override fun deleteActivity(activityId: String) {
        presenter.deleteActivity(activityId)
    }

    override fun deleteActivitySuccess() {
        progressDialog.dismiss()
        val intent = Intent()
        intent.putExtra("position", holderPosition)
        if (holderPosition != -1)
            setResult(IntentResultCode.ACTIVITY_DELETE, intent)
        finish()
    }

    override fun deleteActivityFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe("deleteActivityFailed")
    }
}
