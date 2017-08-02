package com.yetland.crazy.bundle.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.yetland.crazy.bundle.main.contract.MainContract
import com.yetland.crazy.bundle.main.contract.MainModel
import com.yetland.crazy.bundle.main.contract.MainPresent
import com.yetland.crazy.bundle.user.UserDataActivity
import com.yetland.crazy.bundle.user.contract.*
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View, UserDataContract.View,
        FollowContract.View, RecyclerViewListener {

    var mainPresent = MainPresent(MainModel(), this)
    var userDataPresent = UserDataPresenter(UserDataModel(), this)
    var followPresent = FollowPresenter(FollowModel(), this)

    val map = HashMap<String, String>()
    var currentUser: _User = SharedPreferencesUtils.getUserInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Main"
        rvMainList.initView(this)
        rvMainList.recyclerViewListener = this
        onLoading("Loading")

        if (currentUser.objectId.isNotEmpty()) {
            val map = HashMap<String, Any>()
            map.put("follower", Point("_User", currentUser.objectId))
            getUser(currentUser.objectId)
            getFollower(map, -1)
        } else {
            getActivities(null, currentPage)
        }
    }

    override fun onLoading(msg: String) {
        rvMainList.onLoading()
    }

    override fun onError(msg: String) {
        rvMainList.onLoadError(msg)
    }

    override fun onComplete(activityModel: Data<ActivityInfo>) {

        LogUtils.e("onComplete")
        val list = ArrayList<BaseEntity>()
        list.addAll(activityModel.results!!)
        rvMainList.onDefaultComplete(list, currentPage)
    }

    override fun onRefresh() {
        currentUser = SharedPreferencesUtils.getUserInfo()
        currentPage = 0

        if (currentUser.objectId.isNotEmpty()) {
            val map = HashMap<String, Any>()
            map.put("follower", Point("_User", currentUser.objectId))
            getUser(currentUser.objectId)
            getFollower(map, -1)
        } else {
            getActivities(null, currentPage)
        }
    }

    override fun onLoadMore() {
        currentPage++
        LogUtils.e("onLoadMore")
        getActivities(null, currentPage)
    }

    override fun onErrorClick() {
        onLoading("Loading")
        currentPage = 0
        getActivities(null, currentPage)
    }

    override fun getActivities(where: String?, skip: Int) {
        mainPresent.getActivities(where, currentPage)
    }


    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_user -> {
                if (isLogin) {
                    val intent = Intent(activity, UserDataActivity::class.java)
                    startActivityForResult(intent, IntentRequestCode.MAIN_TO_USER_DATA)
                } else {
                    showLogin()
                }
            }
            R.id.menu_search -> {
                ToastUtils.showShortSafe("Search")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDataChanged() {
        if (isFollowListChanged) {
            rvMainList.adapter.notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtils.e("resultCode = $resultCode")
        when (resultCode) {
            IntentResultCode.LOG_OUT -> {
                rvMainList.adapter.notifyDataSetChanged()
            }
            IntentResultCode.LOG_IN -> {
                rvMainList.adapter.notifyDataSetChanged()
            }
            IntentResultCode.MAIN_TO_DETAIL_RESULT -> {
                val bundle = data?.extras
                val activityInfo: ActivityInfo = bundle?.getSerializable("activityInfo") as ActivityInfo
                val position = bundle.getInt("position")

                if (rvMainList.adapter.mList[position].objectId == activityInfo.objectId) {
                    rvMainList.adapter.mList[position] = activityInfo
                    rvMainList.adapter.notifyDataSetChanged()
                }
            }
            IntentResultCode.ACTIVITY_DELETE -> {
                val position = data?.getIntExtra("position", 0)
                if (position != null) {
                    rvMainList.adapter.mList.removeAt(position)
                    rvMainList.adapter.notifyDataSetChanged()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun getUser(objectId: String) {
        userDataPresent.getUser(objectId)
    }

    override fun getUserFailed(msg: String) {
    }

    override fun getUserSuccess(user: _User) {
        SharedPreferencesUtils.saveUserInfo(user)
    }

    override fun updateUser(user: _User, map: HashMap<String, Any>) {}

    override fun updateUserSuccess() {}

    override fun updateUserFailed(msg: String) {}

    override fun getFollower(map: HashMap<String, Any>, page: Int) {
        followPresent.getFollower(Gson().toJson(map), page)
    }

    override fun follow(follow: CommitFollow) {
    }

    override fun unFollow(objectId: String) {
    }

    override fun getFollowerSuccess(data: Data<Follow>) {
        SharedPreferencesUtils.saveString(SharedPreferencesConstant.KEY_FOLLOWER_LIST, Gson().toJson(data.results))
        getActivities(null, currentPage)
    }

    override fun followSuccess(follow: Follow) {
    }

    override fun unFollowSuccess() {
    }

    override fun getFollowerFailed(msg: String) {
        rvMainList.onLoadError(msg)
    }

    override fun followFailed(msg: String) {
    }

    override fun unFollowFailed(msg: String) {
    }
}
