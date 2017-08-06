package com.yetland.crazy.bundle.user.holder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.yetland.crazy.bundle.user.detail.UserDetailActivity
import com.yetland.crazy.bundle.user.contract.FollowContract
import com.yetland.crazy.bundle.user.contract.FollowModel
import com.yetland.crazy.bundle.user.contract.FollowPresenter
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.item_follower.view.*

/**
 * @Name: FollowHolder
 * @Author: yeliang
 * @Date: 2017/7/16
 */
class FollowHolder constructor(view: View) : BaseViewHolder<BaseEntity>(view), FollowContract.View {

    var ivFollowAvatar = view.iv_follow_avatar
    var ivUnFollow = view.iv_un_follow
    var ivFollow = view.iv_follow
    var tvUsername = view.tv_name
    var llFollowUser = view.ll_follow_user

    var follow = Follow()

    var currentUser = _User()// 当前登录的人
    var showUser = _User()// 要显示的人
    var user = _User()// 与showUser对立

    var isMe = false
    var isLogin = false
    var isFollower = false

    var presenter = FollowPresenter(FollowModel(), this)

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

                if (follow.isFollower) {
                    // 关注我的
                    isFollower = true
                    showUser = follow.follower
                    user = follow.user
                } else {
                    // 我的关注
                    isFollower = false
                    showUser = follow.user
                    user = follow.follower
                }
                if (currentUser.objectId == user.objectId) {
                    // 当前主页为我本人
                    isMe = true
                    if (isFollower) {
                        ivFollow.visibility = View.GONE
                        ivUnFollow.visibility = View.GONE
                    } else {
                        ivFollow.visibility = View.GONE
                        ivUnFollow.visibility = View.VISIBLE
                    }
                } else {
                    // 他人主页,只可关注，不可取关
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
                        .placeholder(R.mipmap.image_default)
                        .into(ivFollowAvatar)
            } else {
                Picasso.with(activity)
                        .load(R.mipmap.image_default)
                        .into(ivFollowAvatar)
            }

            ivUnFollow.setOnClickListener({
                if (!isLogin) {
                    showLogin()
                    ToastUtils.showShortSafe("Please login")
                } else {
                    if (isMe) {
                        unFollow(follow.objectId)
                    }
                }
            })

            ivFollow.setOnClickListener({
                if (!isLogin) {
                    showLogin()
                    ToastUtils.showShortSafe("Please login")
                } else {
                    val commitFollow = CommitFollow()
                    commitFollow.follower = Point("_User", currentUser.objectId)
                    commitFollow.user = Point("_User", showUser.objectId)
                    follow(commitFollow)
                    LogUtils.e("followClick" + commitFollow.toString())
                }
            })

            llFollowUser.setOnClickListener({
                val intent = Intent(mActivity, UserDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("showUser", showUser)
                intent.putExtras(bundle)
                mActivity.startActivity(intent)
            })
        }
    }

    override fun getFollower(map: HashMap<String, Any>, page: Int) {
    }

    override fun follow(follow: CommitFollow) {
        progressDialog.setContent("Following...")
        progressDialog.show()
        presenter.follow(follow)
    }

    override fun unFollow(objectId: String) {
        progressDialog.setContent("UnFollowing...")
        progressDialog.show()
        presenter.unFollow(objectId)
    }

    override fun getFollowerSuccess(data: Data<Follow>) {
    }

    override fun followSuccess(follow: Follow) {
        progressDialog.dismiss()
        val followList = SharedPreferencesUtils.getFollowLists()
        if (!followId.contains(follow.user.objectId)) {
            followList.add(follow)
            SharedPreferencesUtils.saveString(SharedPreferencesConstant.KEY_FOLLOWER_LIST, Gson().toJson(followList))
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

        SharedPreferencesUtils.saveString(SharedPreferencesConstant.KEY_FOLLOWER_LIST, Gson().toJson(followList))

        progressDialog.dismiss()
        mAdapter.mList.removeAt(mPosition)
        mAdapter.notifyDataSetChanged()
    }

    override fun getFollowerFailed(msg: String) {
        ToastUtils.showShortSafe(msg)
    }

    override fun followFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }

    override fun unFollowFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }
}