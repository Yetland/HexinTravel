package com.yetland.crazy.bundle.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.yetland.crazy.bundle.main.contract.MainContract
import com.yetland.crazy.bundle.main.contract.MainModel
import com.yetland.crazy.bundle.main.contract.MainPresent
import com.yetland.crazy.bundle.user.contract.*
import com.yetland.crazy.core.base.BaseFragment
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.ynchinamobile.hexinlvxing.R

class MainFragment : BaseFragment(), MainContract.View, UserDataContract.View,
        FollowContract.View, RecyclerViewListener {

    var mainPresent = MainPresent(MainModel(), this)
    var userDataPresent = UserDataPresenter(UserDataModel(), this)
    var followPresent = FollowPresenter(FollowModel(), this)

    val map = HashMap<String, String>()
    var currentUser: _User = SharedPreferencesUtils.getUserInfo()

    lateinit var rvMainList: BaseRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (currentView == null) {

            currentView = inflater!!.inflate(R.layout.fragment_main, container, false)
            rvMainList = currentView!!.findViewById(R.id.rvMainList)
            rvMainList.initView(activity)
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
        return currentView
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

    override fun onDataChanged() {
        rvMainList.adapter.notifyDataSetChanged()
    }

    fun onItemChanged(position: Int, activityInfo: ActivityInfo) {
        if (rvMainList.adapter.mList[position].objectId == activityInfo.objectId) {
            rvMainList.adapter.mList[position] = activityInfo
            rvMainList.adapter.notifyDataSetChanged()
        }
    }

    fun onItemDelete(position: Int) {
        rvMainList.adapter.mList.removeAt(position)
        rvMainList.adapter.notifyDataSetChanged()
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
