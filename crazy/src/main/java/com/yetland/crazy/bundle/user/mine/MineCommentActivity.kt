package com.yetland.crazy.bundle.user.mine

import android.os.Bundle
import android.view.View
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.MyComment
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_mine_comment.*
import kotlinx.android.synthetic.main.include_action_bar_text.*

class MineCommentActivity : BaseActivity(), MyCommentContract.View, RecyclerViewListener {

    var presenter = MyCommentPresenter(MyCommentModel(), this)
    val map = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_comment)

        setSupportActionBar(toolbar2)
        supportActionBar?.title = "MyComment"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvAction.text = "删除全部"
        tvAction.visibility = View.GONE

        rvMyComment.initView(activity)
        rvMyComment.onLoading()
        rvMyComment.recyclerViewListener = this

        val user = SharedPreferencesUtils.getUserInfo()
        if (user.username.isEmpty()) {
            rvMyComment.onLoadError("User is null , try to logout then login")
            finish()
        } else {
            map.put("creatorId", user.objectId)
            getMyComment(map, currentPage)
        }

        tvAction.setOnClickListener({
            deleteAllComment(currentLoginUser.objectId)
        })
    }

    override fun deleteAllComment(userId: String) {
        progressDialog.show()
        presenter.deleteAllComment(userId)
    }

    override fun deleteSuccess() {
        progressDialog.dismiss()
        ToastUtils.showShortSafe("deleteSuccess")
        finish()
    }

    override fun deleteFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe("deleteFailed")
    }

    override fun getMyComment(where: HashMap<String, String>, page: Int) {
        presenter.getMyComment(where, page)
    }

    override fun getMyCommentSuccess(data: Data<MyComment>) {

        tvAction.visibility = View.VISIBLE
        val list = ArrayList<BaseEntity>()
        list.addAll(data.results!!)
        rvMyComment.onDefaultComplete(list, currentPage)
        if (currentPage == 0 && list.size == 0) {
            tvAction.visibility = View.GONE
        }
    }

    override fun failed(msg: String) {
        rvMyComment.onLoadError(msg)
    }

    override fun onRefresh() {
        currentPage = 0
        getMyComment(map, currentPage)
    }

    override fun onLoadMore() {
        currentPage++
        getMyComment(map, currentPage)
    }

    override fun onErrorClick() {
        currentPage = 0
        rvMyComment.onLoading()
        getMyComment(map, currentPage)
    }
}
