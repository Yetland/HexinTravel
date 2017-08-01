package com.yetland.crazy.bundle.main.holder

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.yetland.crazy.bundle.main.contract.ActivityHolderContract
import com.yetland.crazy.bundle.main.contract.ActivityHolderModel
import com.yetland.crazy.bundle.main.contract.ActivityHolderPresenter
import com.yetland.crazy.bundle.main.detail.DetailActivity
import com.yetland.crazy.bundle.user.contract.FollowContract
import com.yetland.crazy.bundle.user.contract.FollowModel
import com.yetland.crazy.bundle.user.contract.FollowPresenter
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import com.afollestad.materialdialogs.MaterialDialog
import com.yetland.crazy.bundle.user.UserDetailActivity
import com.yetland.crazy.core.utils.ImageUtils
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_main_activity.view.*

/**
 * @Name:           ActivityHolder
 * @Author:         yeliang
 * @Date:           2017/7/11
 */
class ActivityHolder constructor(itemView: View) : BaseViewHolder<BaseEntity>(itemView), View.OnClickListener,
        ActivityHolderContract.View, FollowContract.View {


    var presenter = ActivityHolderPresenter(ActivityHolderModel(), this)
    var followPresenter = FollowPresenter(FollowModel(), this)

    var tvTitle = itemView.tv_title
    var tvContent = itemView.tv_content
    var ivPhoto = itemView.iv_photo
    var ivAvatar = itemView.iv_avatar
    var tvUserName = itemView.tv_user_name
    var tvTime = itemView.tv_time
    var tvLike = itemView.tv_like
    var ivLike = itemView.iv_like
    var llLike = itemView.ll_like
    var llComment = itemView.ll_comment
    var llActivityCreator = itemView.ll_activity_creator

    var tvComment = itemView.tv_comment
    var ivFollow = itemView.iv_follow

    val llActivityMyComment = itemView.ll_activity_my_comment
    val tvCommentUsername = itemView.tv_name
    val ivCommentAvatar = itemView.iv_comment_avatar
    var tvCommentContent = itemView.tv_comment_content
    var tvCommentTime = itemView.tv_comment_time

    var comment = MyComment()
    var activityInfo = ActivityInfo()
    var updatedActivityInfo = ActivityInfo()
    var holderPosition = 0
    var currentUser = _User()
    var activityCreator = _User()
    lateinit var adapter: BaseAdapter<BaseEntity>

    var followList = ArrayList<Follow>()
    var followId = ArrayList<String>()

    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>, activity: Activity) {
        mActivity = activity
        this.adapter = adapter
        this.holderPosition = position
        currentUser = SharedPreferencesUtils.getUserInfo()

        followId = SharedPreferencesUtils.getFollowList()

        if (t is MyComment) {
            comment = t
            llActivityMyComment.visibility = View.VISIBLE

            if (comment.activity.objectId.isNotEmpty()) {
                comment.activity.clickable = false
                setData(comment.activity)
            }
            setCommentData(comment)
        } else if (t is ActivityInfo) {
            llActivityMyComment.visibility = View.GONE
            setData(t)
        }
    }

    fun setCommentData(comment: MyComment) {
        tvCommentContent.text = comment.content
        tvCommentTime.text = comment.createdAt
        val creator = comment.creator
        tvCommentUsername.text = creator.username

        if (creator.avatarUrl!!.isNotEmpty()) {
            Picasso.with(context)
                    .load(creator.avatarUrl)
                    .placeholder(R.mipmap.image_default)
                    .into(ivCommentAvatar)
        }
    }

    fun setData(t: ActivityInfo) {
        activityInfo = t
        updatedActivityInfo = activityInfo
        tvTitle.text = activityInfo.title
        tvContent.text = activityInfo.content
        tvTime.text = activityInfo.createdAt

        ivLike.setImageDrawable(context.resources.getDrawable(R.mipmap.ic_like))
        if (currentUser.objectId.isNotEmpty()
                && activityInfo.like.isNotEmpty()
                && activityInfo.like.contains(currentUser.objectId)) {
            ivLike.setImageDrawable(context.resources.getDrawable(R.mipmap.ic_liked))
        }

        tvLike.text = activityInfo.likeCount.toString()
        tvComment.text = activityInfo.commentCount.toString()

        if (!TextUtils.isEmpty(activityInfo.url)) {
            ivPhoto.adjustViewBounds = true
            Picasso.with(context)
                    .load(activityInfo.url!!.split(";")[0])
                    .transform(transform)
                    .placeholder(R.mipmap.img_custom)
                    .into(ivPhoto)
        }
        activityCreator = activityInfo.creator
        tvUserName.text = activityCreator.username
        if (activityCreator.avatarUrl != null) {
            Picasso.with(context)
                    .load(activityCreator.avatarUrl)
                    .placeholder(R.mipmap.image_default)
                    .into(ivAvatar)
        } else {
            ivAvatar.setImageDrawable(context.resources.getDrawable(R.mipmap.image_default))
        }

        if (activityInfo.clickable) {
            itemView.setOnClickListener(this)
            llComment.setOnClickListener(this)
        }

        if (followId.contains(activityCreator.objectId)) {
            ivFollow.visibility = View.GONE
        } else {
            ivFollow.visibility = View.VISIBLE
        }

        ivFollow.setOnClickListener(this)
        llLike.setOnClickListener(this)
        llActivityCreator.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view == itemView) {
            LogUtils.e("itemViewClick")
            val intent = Intent(mActivity, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("activityInfo", updatedActivityInfo)
            bundle.putInt("position", holderPosition)
            intent.putExtras(bundle)
            mActivity.startActivityForResult(intent, IntentRequestCode.MAIN_TO_DETAIL)
        }
        when (view.id) {
            R.id.ll_activity_creator -> {
                val intent = Intent(mActivity, UserDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("showUser", activityCreator)
                intent.putExtras(bundle)

                mActivity.startActivityForResult(intent, IntentRequestCode.MAIN_TO_USER_DATA)
            }

            R.id.iv_follow -> {
                if (currentUser.objectId.isNotEmpty()) {

                    progressDialog = MaterialDialog.Builder(mActivity)
                            .content("Committing")
                            .progress(true, 0)
                            .cancelable(false)
                            .show()
                    val commitFollow = CommitFollow()
                    commitFollow.follower = Point("_User", currentUser.objectId)
                    commitFollow.user = Point("_User", activityCreator.objectId)
                    follow(commitFollow)
                    LogUtils.e("followClick" + commitFollow.toString())
                } else {
                    showLogin()
                    ToastUtils.showShortSafe("Please login")
                }
            }
            R.id.ll_like -> {
                LogUtils.e("likeClick")
                if (currentUser.objectId.isNotEmpty()) {
                    val map = HashMap<String, Any>()
                    if (updatedActivityInfo.like.isEmpty()
                            || !updatedActivityInfo.like.contains(currentUser.objectId)) {
                        map.put("like", updatedActivityInfo.like + currentUser.objectId + ";")
                        updatedActivityInfo.like += currentUser.objectId + ";"
                    }

                    map.put("likeCount", ++updatedActivityInfo.likeCount)
                    val where = Gson().toJson(map)
                    like(activityInfo.objectId, where)
                } else {
                    showLogin()
                    ToastUtils.showShortSafe("Please login")
                }
            }
            R.id.ll_comment -> {
                LogUtils.e("commentClick")
            }
        }
    }

    override fun like(activityId: String, where: String) {
        LogUtils.e(where)
        presenter.like(activityId, where)
    }

    override fun cancelLike(activityId: String, where: String) {
        presenter.cancelLike(activityId, where)
    }

    override fun likeSuccess() {
        activityInfo = updatedActivityInfo
        if (comment.activity.objectId.isNotEmpty()) {
            comment.activity = updatedActivityInfo
            adapter.mList[holderPosition] = comment
        } else {
            adapter.mList[holderPosition] = updatedActivityInfo
        }
        adapter.notifyItemChanged(holderPosition)
        LogUtils.e("likeSuccess")
    }

    override fun cancelLikeSuccess() {
        LogUtils.e("cancelLikeSuccess")
    }

    override fun fail(errorMsg: String) {
        ToastUtils.showShortSafe(errorMsg)
    }


    override fun getFollower(map: HashMap<String, Any>, page: Int) {
    }

    override fun follow(follow: CommitFollow) {
        followPresenter.follow(follow)
    }

    override fun unFollow(objectId: String) {
    }

    override fun getFollowerSuccess(data: Data<Follow>) {
    }

    override fun followSuccess(follow: Follow) {
        progressDialog.dismiss()
        followList = SharedPreferencesUtils.getFollowLists()
        if (!followId.contains(follow.user.objectId)) {
            followList.add(follow)
            SharedPreferencesUtils.saveString(SharedPreferencesConstant.KEY_FOLLOWER_LIST, Gson().toJson(followList))
        }
        adapter.notifyDataSetChanged()
    }

    override fun unFollowSuccess() {
    }

    override fun getFollowerFailed(msg: String) {
    }

    override fun followFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }

    override fun unFollowFailed(msg: String) {
    }


    val transform = (object : Transformation {
        override fun transform(source: Bitmap): Bitmap {
            val result = ImageUtils.compressByQuality(source, (100 * 1024).toLong(), true)
            return result
        }

        override fun key(): String = "square()"
    })
}