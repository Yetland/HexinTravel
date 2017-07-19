package com.yetland.crazy.bundle.user.holder

import android.app.Activity
import android.app.ProgressDialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.yetland.crazy.bundle.user.contract.FollowContract
import com.yetland.crazy.bundle.user.contract.FollowModel
import com.yetland.crazy.bundle.user.contract.FollowPresenter
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
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
    var tvUsername = view.findViewById<TextView>(R.id.tv_name)

    var follower = Follow()
    var user = _User()

    val model = FollowModel()
    var presenter = FollowPresenter(model, this)
    lateinit var dialog: MaterialDialog

    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>, activity: Activity) {
        if (t is Follow) {
            follower = t
            mPosition = position
            mAdapter = adapter
            mActivity = activity
            if (follower.isFollower) {
                ivUnFollow.visibility = View.GONE
                user = follower.follower
            } else {
                ivUnFollow.visibility = View.VISIBLE
                user = follower.user
            }

            tvUsername.text = user.username
            if (user.avatarUrl != null) {
                Picasso.with(activity)
                        .load(user.avatarUrl)
                        .placeholder(R.mipmap.huas)
                        .into(ivFollowAvatar)
            }else{
                Picasso.with(activity)
                        .load(R.mipmap.huas)
                        .into(ivFollowAvatar)
            }

            ivUnFollow.setOnClickListener({
                unFollow(follower.objectId)
            })
        }
    }

    override fun getFollower(map: HashMap<String, Any>, page: Int) {
    }

    override fun follow(follow: CommitFollow) {
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
    }

    override fun unFollowSuccess() {
        val followList = SharedPreferencesUtils.getFollowLists()
        LogUtils.e("followList size before = ${followList.size}")

        for (x in followList) {
            if (x.user.objectId == follower.user.objectId) {
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
    }

    override fun followFailed(msg: String) {
    }

    override fun unFollowFailed(msg: String) {
        dialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }
}