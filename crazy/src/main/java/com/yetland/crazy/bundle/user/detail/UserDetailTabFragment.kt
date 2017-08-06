package com.yetland.crazy.bundle.user.detail

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.yetland.crazy.bundle.main.contract.MainContract
import com.yetland.crazy.bundle.main.contract.MainModel
import com.yetland.crazy.bundle.main.contract.MainPresent
import com.yetland.crazy.bundle.user.contract.FollowContract
import com.yetland.crazy.bundle.user.contract.FollowModel
import com.yetland.crazy.bundle.user.contract.FollowPresenter
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.LogUtils
import com.ynchinamobile.hexinlvxing.R

/**
 * @Name:           UserDetailTabFragment
 * @Author:         yeliang
 * @Date:           2017/7/20
 */
class UserDetailTabFragment : Fragment(), MainContract.View, FollowContract.View, RecyclerViewListener {

    val TAB_FOLLOWER = 1001
    val TAB_FOLLOWEE = 1002
    val TAB_ACTIVITY = 1003

    var openType = 1001
    var currentUser = _User()
    var currentPage = 0

    lateinit var rvUserDetail: BaseRecyclerView

    var activityPresenter = MainPresent(MainModel(), this)
    var followPresenter = FollowPresenter(FollowModel(), this)

    var followKey = "follower"
    val map = HashMap<String, Any>()

    var mView: View? = null

    lateinit var pref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (mView == null) {
            mView = inflater?.inflate(R.layout.fragment_user_detail_tab, container, false)
            rvUserDetail = mView!!.findViewById(R.id.rv_user_detail)

            pref = activity.getSharedPreferences(SharedPreferencesConstant.PREF_NAME, 0)
            pref.registerOnSharedPreferenceChangeListener(prefListener)

            if (currentUser.objectId.isEmpty()) {
                rvUserDetail.onLoadError("User not exist")
                rvUserDetail.isErrorClickable = false
            } else {
                rvUserDetail.initView(activity)
                rvUserDetail.recyclerViewListener = this
                rvUserDetail.onLoading()

                getData()
            }
        }
        return mView
    }

    val prefListener = SharedPreferences.OnSharedPreferenceChangeListener({
        sharedPreferences, s ->
        LogUtils.e("OnSharedPreferenceChangeListener , key = $s")
        if (openType == TAB_FOLLOWER) {
            onRefresh()
        } else {
            rvUserDetail.adapter.notifyDataSetChanged()
        }
    })


    fun getData() {
        when (openType) {
            TAB_FOLLOWER -> {
                followKey = "user"
                map.put(followKey, Point("_User", currentUser.objectId))
                getFollower(map, currentPage)
            }
            TAB_FOLLOWEE -> {
                followKey = "follower"
                map.put(followKey, Point("_User", currentUser.objectId))
                getFollower(map, currentPage)
            }
            TAB_ACTIVITY -> {
                map.put("creator", Point("_User", currentUser.objectId))
                getActivities(Gson().toJson(map), currentPage)
            }
        }
    }

    override fun onRefresh() {
        currentPage = 0
        getData()
    }

    override fun onLoadMore() {
        currentPage++
        getData()
    }

    override fun onErrorClick() {
        rvUserDetail.onLoading()
        currentPage = 0
        getData()
    }

    override fun getActivities(where: String?, skip: Int) {
        activityPresenter.getActivities(where, skip)
    }

    override fun onLoading(msg: String) {
    }

    override fun getFollower(map: HashMap<String, Any>, page: Int) {
        followPresenter.getFollower(Gson().toJson(map), page)
    }

    override fun onError(msg: String) {
        rvUserDetail.onLoadError(msg)
    }

    override fun onComplete(activityModel: Data<ActivityInfo>) {
        val list = ArrayList<BaseEntity>()
        list.addAll(activityModel.results!!)
        rvUserDetail.onDefaultComplete(list, currentPage)
    }

    override fun follow(follow: CommitFollow) {
    }

    override fun unFollow(objectId: String) {
    }

    override fun getFollowerSuccess(data: Data<Follow>) {
        val result = data.results!!
        result.map {
            it.isFollower = (followKey == "user")
        }
        val list = ArrayList<BaseEntity>()
        list.addAll(result)
        rvUserDetail.onDefaultComplete(list, currentPage)
    }

    override fun followSuccess(follow: Follow) {
    }

    override fun unFollowSuccess() {
    }

    override fun getFollowerFailed(msg: String) {
        rvUserDetail.onLoadError(msg)
    }

    override fun followFailed(msg: String) {
    }

    override fun unFollowFailed(msg: String) {
    }

}