package com.yetland.crazy.bundle.user.holder

import android.app.Activity
import android.app.ProgressDialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yetland.crazy.bundle.user.contract.FollowContract
import com.yetland.crazy.bundle.user.contract.FollowModel
import com.yetland.crazy.bundle.user.contract.FollowPresenter
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.Follow
import com.yetland.crazy.core.entity._User
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
    lateinit var dialog: ProgressDialog

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
            }

            ivUnFollow.setOnClickListener({
                unFollow(follower.objectId)
            })
        }
    }

    override fun getFollower(map: HashMap<String, Any>, page: Int) {
    }

    override fun getFollowee(map: HashMap<String, Any>, page: Int) {
    }

    override fun follow(follow: Follow) {
    }

    override fun unFollow(objectId: String) {
        dialog = ProgressDialog(mActivity)
        dialog.setMessage("LOADING...")
        dialog.setCancelable(false)
        dialog.show()

        presenter.unFollow(objectId)
    }

    override fun getFollowerSuccess(data: Data<Follow>) {
    }

    override fun getFolloweeSuccess(data: Data<Follow>) {
    }

    override fun followSuccess(follow: Follow) {
    }

    override fun unFollowSuccess() {
        dialog.dismiss()
        mAdapter.mList.removeAt(mPosition)
        mAdapter.notifyDataSetChanged()
    }

    override fun getFollowerFailed(msg: String) {
    }

    override fun getFolloweeFailed(msg: String) {
    }

    override fun followFailed(msg: String) {
    }

    override fun unFollowFailed(msg: String) {
        dialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }
}