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
import com.yetland.crazy.bundle.user.ImageActivity
import com.yetland.crazy.bundle.user.UserDetailActivity
import com.yetland.crazy.core.utils.ImageUtils
import kotlinx.android.synthetic.main.include_activity_forward.view.*
import kotlinx.android.synthetic.main.include_activity_photo.view.*
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

    var llActivity = itemView.ll_activity
    var tvDeleted = itemView.tv_deleted
    var tvTitle = itemView.tv_title
    var ivPhoto = itemView.iv_photo
    var ivAvatar = itemView.iv_avatar
    var tvUserName = itemView.tv_user_name
    var tvTime = itemView.tv_time
    var tvLike = itemView.tv_like
    var ivLike = itemView.iv_like
    var llLike = itemView.ll_like

    var llComment = itemView.ll_comment
    var llForward = itemView.ll_forward
    var tvForward = itemView.tv_forward
    val ivForwardPhoto = itemView.iv_forward_photo

    val inForward = itemView.in_forward
    val tvForwardUserName = itemView.tv_forward_user_name
    val tvForwardTitle = itemView.tv_forward_title
    val tvForwardDeleted = itemView.tv_forward_deleted
    val llForwardDetail = itemView.ll_forward_detail
    val llForwardSeeBefore = itemView.ll_forward_see_before

    val ivHot = itemView.iv_hot

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
            } else {
                llActivity.visibility = View.GONE
                tvDeleted.visibility = View.VISIBLE
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
        tvTime.text = activityInfo.createdAt

        ivLike.setImageDrawable(context.resources.getDrawable(R.mipmap.ic_like))
        if (currentUser.objectId.isNotEmpty()
                && activityInfo.like.isNotEmpty()
                && activityInfo.like.contains(currentUser.objectId)) {
            ivLike.setImageDrawable(context.resources.getDrawable(R.mipmap.ic_liked))
        }

        tvLike.text = activityInfo.likeCount.toString()
        tvComment.text = activityInfo.commentCount.toString()
        tvForward.text = activityInfo.forwardCount.toString()

        if (activityInfo.likeCount > 99) {
            ivHot.visibility = View.VISIBLE
        } else {
            ivHot.visibility = View.GONE
        }
        if (activityInfo.forward) {

            ivPhoto.visibility = View.GONE
            inForward.visibility = View.VISIBLE
            if (activityInfo.forwardActivity == null) {
                tvForwardDeleted.visibility = View.VISIBLE
                llForwardDetail.visibility = View.GONE
            } else {
                tvForwardDeleted.visibility = View.GONE
                llForwardDetail.visibility = View.VISIBLE

                val forwardActivity = activityInfo.forwardActivity
                val userName = "@" + forwardActivity!!.creator.username
                tvForwardUserName.text = userName
                tvForwardTitle.text = forwardActivity.title

                if (forwardActivity.url.isNotEmpty()) {
                    ivForwardPhoto.visibility = View.VISIBLE
                    ivForwardPhoto.adjustViewBounds = true
                    Picasso.with(context)
                            .load(forwardActivity.url.split(";")[0])
                            .transform(transform)
                            .placeholder(R.mipmap.img_custom)
                            .into(ivForwardPhoto)
                    ivForwardPhoto.setOnClickListener({
                        val intent = Intent(mActivity, ImageActivity::class.java)
                        intent.putExtra("url", forwardActivity.url)
                        mActivity.startActivity(intent)
                    })
                } else {
                    ivForwardPhoto.visibility = View.GONE
                }

                if (forwardActivity.forward) {
                    llForwardSeeBefore.visibility = View.VISIBLE
                    llForwardSeeBefore.setOnClickListener(this)
                } else {
                    llForwardSeeBefore.visibility = View.GONE
                }

                tvForwardUserName.setOnClickListener(this)
                llForwardDetail.setOnClickListener(this)
            }
        } else {
            ivPhoto.visibility = View.VISIBLE
            inForward.visibility = View.GONE
            if (activityInfo.url.isNotEmpty()) {
                ivPhoto.adjustViewBounds = true
                Picasso.with(context)
                        .load(activityInfo.url.split(";")[0])
                        .transform(transform)
                        .placeholder(R.mipmap.img_custom)
                        .into(ivPhoto)
                ivPhoto.setOnClickListener({
                    val intent = Intent(mActivity, ImageActivity::class.java)
                    intent.putExtra("url", activityInfo.url)
                    mActivity.startActivity(intent)
                })
            } else {
                ivPhoto.visibility = View.GONE
            }
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
        }

        if (followId.contains(activityCreator.objectId)) {
            ivFollow.visibility = View.GONE
        } else {
            ivFollow.visibility = View.VISIBLE
        }

        ivFollow.setOnClickListener(this)
        llLike.setOnClickListener(this)
        llForward.setOnClickListener(this)
        ivAvatar.setOnClickListener(this)
        tvUserName.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view == itemView) {
            gotoActivityDetail(activityInfo.objectId, holderPosition)
        }
        when (view.id) {
            R.id.ll_forward_see_before -> {
                gotoActivityDetail(activityInfo.forwardActivity!!.forwardActivity!!.objectId, holderPosition)

            }
            R.id.ll_forward_detail -> {
                gotoActivityDetail(activityInfo.forwardActivity!!.objectId, holderPosition)
            }
            R.id.ll_forward -> {
                if (currentUser.objectId.isEmpty()) {
                    showLogin()
                    ToastUtils.showShortSafe("Please login")
                } else {
                    val dialog = MaterialDialog.Builder(mActivity)
                    dialog.title("转发")
                            .inputRange(1, 150)
                            .input("Write something", null, false, {
                                d, input ->
                                if (input.isEmpty()) {
                                    ToastUtils.showShortSafe("Empty content")
                                } else {
                                    d.dismiss()
                                    val forwardActivity = Point("Activity", activityInfo.objectId)
                                    val creator = Point("_User", currentUser.objectId)

                                    val c = CreateActivityInfo()
                                    c.creator = creator
                                    c.forwardActivity = forwardActivity
                                    c.title = input.toString().trim()
                                    c.forward = true
                                    forward(c)
                                }
                            })
                            .positiveText("评论")
                            .negativeText("取消")
                            .show()
                }
            }
            R.id.tv_user_name -> {
                gotoUserDetail(activityCreator)
            }
            R.id.iv_avatar -> {
                gotoUserDetail(activityCreator)
            }
            R.id.tv_forward_user_name -> {
                gotoUserDetail(activityInfo.forwardActivity!!.creator)
            }
            R.id.iv_follow -> {
                if (currentUser.objectId.isNotEmpty()) {

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
                // TODO 增加策略需要改变
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
        }
    }

    private fun gotoActivityDetail(objectId: String, position: Int) {
        LogUtils.e("itemViewClick")
        val intent = Intent(mActivity, DetailActivity::class.java)
        intent.putExtra("activityId", objectId)
        intent.putExtra("position", position)
        mActivity.startActivityForResult(intent, IntentRequestCode.MAIN_TO_DETAIL)
    }

    private fun gotoUserDetail(user: _User) {
        val intent = Intent(mActivity, UserDetailActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("showUser", user)
        intent.putExtras(bundle)
        mActivity.startActivityForResult(intent, IntentRequestCode.MAIN_TO_USER_DATA)
    }

    override fun forward(createActivityInfo: CreateActivityInfo) {
        progressDialog.show()
        presenter.forward(createActivityInfo)
    }

    override fun forwardSuccess() {
        progressDialog.dismiss()
        ToastUtils.showShortSafe("forwardSuccess")
    }

    override fun forwardFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe("forwardFailed")
    }

    override fun like(activityId: String, where: String) {
        LogUtils.e(where)
        presenter.like(activityId, where)
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

    override fun fail(errorMsg: String) {
        ToastUtils.showShortSafe(errorMsg)
    }


    override fun getFollower(map: HashMap<String, Any>, page: Int) {
    }

    override fun follow(follow: CommitFollow) {
        progressDialog.show()
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