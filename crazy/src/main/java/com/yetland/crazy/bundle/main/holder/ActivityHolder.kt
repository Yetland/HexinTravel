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
import com.yetland.crazy.bundle.user.login.LoginActivity
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.SharedPrefrenceUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R


/**
 * @Name:           ActivityHolder
 * @Author:         yeliang
 * @Date:           2017/7/11
 */
class ActivityHolder constructor(itemView: View) : BaseViewHolder<BaseEntity>(itemView), View.OnClickListener,
        ActivityHolderContract.View, FollowContract.View {


    var presenter = ActivityHolderPresenter(ActivityHolderModel(), this)
    var followPresenter = FollowPresenter(FollowModel(), this)

    val TAG = "ActivityHolder"
    var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    var tvContent: TextView = itemView.findViewById(R.id.tv_content)
    var ivPhoto = itemView.findViewById<ImageView>(R.id.iv_photo)
    var ivAvatar = itemView.findViewById<ImageView>(R.id.iv_avatar)
    var tvUserName = itemView.findViewById<TextView>(R.id.tv_user_name)
    var tvTime: TextView = itemView.findViewById<TextView>(R.id.tv_time)
    var tvLike: TextView = itemView.findViewById<TextView>(R.id.tv_like)
    var ivLike: ImageView = itemView.findViewById(R.id.iv_like)
    var llLike: LinearLayout = itemView.findViewById(R.id.ll_like)
    var llComment: LinearLayout = itemView.findViewById(R.id.ll_comment)
    var tvComment: TextView = itemView.findViewById<TextView>(R.id.tv_comment)

    val llActivityMyComment: LinearLayout = itemView.findViewById(R.id.ll_activity_my_comment)
    val tvCommentUsername: TextView = itemView.findViewById(R.id.tv_name)
    val ivCommentAvatar: ImageView = itemView.findViewById(R.id.iv_comment_avatar)
    var tvCommentContent: TextView = itemView.findViewById(R.id.tv_comment_content)
    var tvCommentTime: TextView = itemView.findViewById(R.id.tv_comment_time)

    var comment = MyComment()
    var activityInfo = ActivityInfo()
    var updatedActivityInfo = ActivityInfo()
    var holderPosition = 0
    var currentUser = _User()
    lateinit var adapter: BaseAdapter<BaseEntity>

    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>, activity: Activity) {
        mActivity = activity
        this.adapter = adapter
        this.holderPosition = position
        currentUser = SharedPrefrenceUtils.getUserInfo(context)
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
                    .placeholder(R.mipmap.huas)
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
//                        .transform(transform)
                    .placeholder(R.mipmap.img_custom)
                    .into(ivPhoto)
        }
        val user = activityInfo.creator
        tvUserName.text = user.username
        if (user.avatarUrl != null) {
            Picasso.with(context)
                    .load(user.avatarUrl)
                    .placeholder(R.mipmap.huas)
                    .into(ivAvatar)
        } else {
            ivAvatar.setImageDrawable(context.resources.getDrawable(R.mipmap.huas))
        }

        if (activityInfo.clickable) {
            itemView.setOnClickListener(this)
            llComment.setOnClickListener(this)
        }
        llLike.setOnClickListener(this)
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
            R.id.bt_follow -> {
                LogUtils.e("followClick")
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
                    mActivity.startActivityForResult(Intent(mActivity, LoginActivity::class.java), IntentRequestCode.MAIN_TO_LOGIN)
                    ToastUtils.showShortSafe("UserId is null")
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

    override fun follow(followUserId: String, followerUserId: String) {
        presenter.follow(followUserId, followerUserId)
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

    override fun followSuccess() {
        LogUtils.e("followSuccess")
    }

    override fun fail(errorMsg: String) {
        ToastUtils.showShortSafe(errorMsg)
    }


    override fun getFollower(map: HashMap<String, Any>, page: Int) {
    }

    override fun getFollowee(map: HashMap<String, Any>, page: Int) {
    }

    override fun follow(follow: Follow) {
    }

    override fun unFollow(objectId: String) {
    }

    override fun getFollowerSuccess(data: Data<Follow>) {
    }

    override fun getFolloweeSuccess(data: Data<Follow>) {
    }

    override fun followSuccess(follow: Follow) {
    }

    override fun unFollowSuccess() {
    }

    override fun getFollowerFailed(msg: String) {
    }

    override fun getFolloweeFailed(msg: String) {
    }

    override fun followFailed(msg: String) {

    }

    override fun unFollowFailed(msg: String) {
    }


    val transform = (object : Transformation {
        override fun transform(source: Bitmap): Bitmap {
            val size = minOf(source.width, source.height)
            val x = (source.width.minus(size)).div(2)
            val y = (source.height.minus(size)).div(2)
            LogUtils.e("transformBefore", "width = ${source.width}, " +
                    "height = ${source.height} , " +
                    "size = $size , " +
                    "x = $x , " +
                    "y = $y")
            val result = Bitmap.createBitmap(source, x, y, size, size)
            if (result != source) {
                source.recycle()
            }
            LogUtils.e("transformAfter", "width = ${result.width}, " +
                    "height = ${result.height} , " +
                    "size = $size")
            return result
        }

        override fun key(): String = "square()"
    })
}