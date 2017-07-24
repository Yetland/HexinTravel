package com.yetland.crazy.bundle.user

import android.os.Bundle
import com.google.gson.Gson
import com.yetland.crazy.bundle.user.contract.FollowContract
import com.yetland.crazy.bundle.user.contract.FollowModel
import com.yetland.crazy.bundle.user.contract.FollowPresenter
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_follow.*

class FollowActivity : BaseActivity(), FollowContract.View, RecyclerViewListener {


    var model = FollowModel()
    var presenter = FollowPresenter(model, this)
    var isFollower = false
    lateinit var currentUser: _User
    var map = HashMap<String, Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)

        isFollower = intent.getBooleanExtra("isFollower", false)
        var key = "follower"
        if (isFollower) {
            supportActionBar?.title = "关注我的"
            key = "user"
        } else {
            supportActionBar?.title = "我关注的"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        currentUser = SharedPreferencesUtils.getUserInfo()

        rvFollow.initView(activity)
        rvFollow.recyclerViewListener = this
        rvFollow.onLoading()

        map.put(key, Point("_User", currentUser.objectId))
        getFollower(map, currentPage)
    }

    override fun getFollower(map: HashMap<String, Any>, page: Int) {
        presenter.getFollower(Gson().toJson(map), page)
    }


    override fun follow(follow: CommitFollow) {
        presenter.follow(follow)
    }

    override fun unFollow(objectId: String) {
        presenter.unFollow(objectId)
    }

    override fun getFollowerSuccess(data: Data<Follow>) {

        val result = data.results!!
        result.map {
            it.isFollower = isFollower
        }
        val list = ArrayList<BaseEntity>()
        list.addAll(result)
        rvFollow.onDefaultComplete(list, currentPage)
    }

    override fun followSuccess(follow: Follow) {
    }

    override fun unFollowSuccess() {
    }

    override fun getFollowerFailed(msg: String) {
        rvFollow.onLoadError(msg)
    }

    override fun followFailed(msg: String) {
    }

    override fun unFollowFailed(msg: String) {
    }

    override fun onRefresh() {
        currentPage = 0
        getFollower(map, currentPage)
    }

    override fun onLoadMore() {
        currentPage++
        getFollower(map, currentPage)
    }

    override fun onErrorClick() {
        rvFollow.onLoading()
        currentPage = 0
        getFollower(map, currentPage)
    }
}
