package com.yetland.crazy.bundle.user.holder

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.yetland.crazy.bundle.user.contract.FollowContract
import com.yetland.crazy.bundle.user.contract.FollowModel
import com.yetland.crazy.bundle.user.contract.FollowPresenter
import com.yetland.crazy.bundle.user.login.LoginActivity
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.constant.SharedPrefrencesConstant
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R

/**
 * @Name: FollowHolder
 * @Author: yeliang
 * @Date: 2017/7/16
 */
class FollowHolder constructor(view: View) : BaseViewHolder<BaseEntity>(view), FollowContract.View {

    var ivFollowAvatar = view.findViewById<ImageView>(R.id.iv_follow_avatar)
    var ivUnFollow = view.findViewById<ImageView>(R.id.iv_un_follow)
    var ivFollow = view.findViewById<ImageView>(R.id.iv_follow)
    var tvUsername = view.findViewById<TextView>(R.id.tv_name)

    var follow = Follow()

    var currentUser = _User()// 当前登录的人
    var showUser = _User()// 要显示的人
    var user = _User()// 与showUser对立

    var isMe = false
    var isLogin = false
    var isFollower = false

    val model = FollowModel()
    var presenter = FollowPresenter(model, this)
    lateinit var dialog: MaterialDialog

    var followId = ArrayList<String>()
    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>, activity: Activity) {
        if (t is Follow) {
            follow = t
            mPosition = position
            mAdapter = adapter
            mActivity = activity

            currentUser = SharedPreferencesUtils.getUserInfo()

            if (currentUser.objectId.isEmpty()) {
                // 未登录用户
                isLogin = false
                isMe = false
                ivFollow.visibility = View.VISIBLE
                ivUnFollow.visibility = View.GONE
            } else {
                // 已登录用户
                isLogin = true
                followId = SharedPreferencesUtils.getFollowList()

                // 显示的是粉丝，
                if (follow.isFollower) {
                    isFollower = true
                    showUser = follow.follower
                    user = follow.user
                } else {
                    isFollower = false
                    showUser = follow.user
                    user = follow.follower
                }
                if (currentUser.objectId == user.objectId) {
                    isMe = true
                    if (isFollower) {
                        ivFollow.visibility = View.GONE
                        ivUnFollow.visibility = View.GONE
                    } else {
                        ivFollow.visibility = View.GONE
                        ivUnFollow.visibility = View.VISIBLE
                    }
                } else {
                    isMe = false
                    if (followId.contains(showUser.objectId)) {
                        ivFollow.visibility = View.GONE
                        ivUnFollow.visibility = View.GONE
                    } else {
                        ivFollow.visibility = View.VISIBLE
                        ivUnFollow.visibility = View.GONE
                    }
                }
            }

            tvUsername.text = showUser.username
            if (showUser.avatarUrl != null) {
                Picasso.with(activity)
                        .load(showUser.avatarUrl)
                        .placeholder(R.mipmap.huas)
                        .into(ivFollowAvatar)
            } else {
                Picasso.with(activity)
                        .load(R.mipmap.huas)
                        .into(ivFollowAvatar)
            }

            ivUnFollow.setOnClickListener({
                if (!isLogin) {
                    mActivity.startActivityForResult(Intent(mActivity, LoginActivity::class.java), IntentRequestCode.MAIN_TO_LOGIN)
                    ToastUtils.showShortSafe("Please login")
                } else {
                    if (isMe) {
                        unFollow(follow.objectId)
                    }
                }
            })

            ivFollow.setOnClickListener({
                if (!isLogin) {
                    mActivity.startActivityForResult(Intent(mActivity, LoginActivity::class.java), IntentRequestCode.MAIN_TO_LOGIN)
                    ToastUtils.showShortSafe("Please login")
                } else {
                    if (isMe) {
                        progressDialog = MaterialDialog.Builder(mActivity)
                                .content("Committing")
                                .progress(true, 0)
                                .cancelable(false)
                                .show()
                        val commitFollow = CommitFollow()
                        commitFollow.follower = Point("Pointer", "_User", currentUser.objectId)
                        commitFollow.user = Point("Pointer", "_User", showUser.objectId)
                        follow(commitFollow)
                        LogUtils.e("followClick" + commitFollow.toString())
                    }
                }
            })
        }
    }

    override fun getFollower(map: HashMap<String, Any>, page: Int) {
    }

    override fun follow(follow: CommitFollow) {
        presenter.follow(follow)
    }

    override fun unFollow(objectId: String) {
        dialog = MaterialDialog.Builder(mActivity)
                .content("LOADING...")
                .cancelable(false)
                .show()
        presenter.unFollow(objectId)
    }

    override fun getFollowerSuccess(data: Data<Follow>) {
    }

    override fun followSuccess(follow: Follow) {
        val followList = SharedPreferencesUtils.getFollowLists()
        if (!followId.contains(follow.user.objectId)) {
            followList.add(follow)
            SharedPreferencesUtils.saveString(SharedPrefrencesConstant.KEY_FOLLOWER_LIST, Gson().toJson(followList))
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun unFollowSuccess() {
        val followList = SharedPreferencesUtils.getFollowLists()
        LogUtils.e("followList size before = ${followList.size}")

        for (x in followList) {
            if (x.user.objectId == follow.user.objectId) {
                followList.remove(x)
                break
            }
        }
        LogUtils.e("followList size after = ${followList.size}")

        SharedPreferencesUtils.saveString(SharedPrefrencesConstant.KEY_FOLLOWER_LIST, Gson().toJson(followList))

        dialog.dismiss()
        mAdapter.mList.removeAt(mPosition)
        mAdapter.notifyDataSetChanged()
    }

    override fun getFollowerFailed(msg: String) {
        ToastUtils.showShortSafe(msg)
    }

    override fun followFailed(msg: String) {
        dialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }

    override fun unFollowFailed(msg: String) {
        dialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }
}