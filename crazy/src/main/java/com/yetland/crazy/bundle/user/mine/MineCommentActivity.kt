package com.yetland.crazy.bundle.user.mine

import android.os.Bundle
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.MyComment
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.ynchinamobile.hexinlvxing.R

class MineCommentActivity : BaseActivity(), MyCommentContract.View, RecyclerViewListener {


    val model = MyCommentModel()
    var presenter = MyCommentPresenter(model, this)
    lateinit var rvMyComment: BaseRecyclerView
    val map = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_comment)

        supportActionBar?.title = "MyComment"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvMyComment = findViewById(R.id.rv_my_comment)
        rvMyComment.initView(activity)
        rvMyComment.onLoading()
        rvMyComment.recyclerViewListener = this

        val user = SharedPreferencesUtils.getUserInfo(activity)
        if (user.username!!.isEmpty()) {
            rvMyComment.onLoadError("User is null , try to logout then login")
            finish()
        } else {
            map.put("creatorId", user.objectId!!)
            getMyComment(map, currentPage)
        }
    }

    override fun getMyComment(where: HashMap<String, String>, page: Int) {
        presenter.getMyComment(where, page)
    }

    override fun getMyCommentSuccess(data: Data<MyComment>) {

        val list = ArrayList<BaseEntity>()
        list.addAll(data.results!!)
        rvMyComment.onDefaultComplete(list, currentPage)
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
