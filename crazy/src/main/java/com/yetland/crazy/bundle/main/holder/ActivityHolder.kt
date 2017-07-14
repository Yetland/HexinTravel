package com.yetland.crazy.bundle.main.holder

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.yetland.crazy.bundle.user.login.LoginActivity
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.FileUtil
import com.yetland.crazy.core.utils.makeShortToast
import com.ynchinamobile.hexinlvxing.R


/**
 * @Name:           ActivityHolder
 * @Author:         yeliang
 * @Date:           2017/7/11
 */
class ActivityHolder constructor(itemView: View) : BaseViewHolder<BaseEntity>(itemView), View.OnClickListener,
        ActivityHolderContract.View {

    var model = ActivityHolderModel()
    var presenter = ActivityHolderPresenter(model, this)

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
    val ivCommentAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
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
        currentUser = FileUtil().getUserInfo(context)
        if (t is MyComment) {
            comment = t
            llActivityMyComment.visibility = View.VISIBLE

            if (comment.activity != null) {
                comment.activity!!.clickable = false
                setData(comment.activity!!)
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
        if (creator != null) {
            tvCommentUsername.text = comment.creator?.username

            Picasso.with(context)
                    .load(comment.creator?.avatarUrl)
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

        if (activityInfo.like.isNotEmpty()) {
            if (currentUser.username!!.isNotEmpty()) {
                val like = activityInfo.like
                if (like.contains(currentUser.objectId.toString())) {
                    ivLike.setImageDrawable(context.resources.getDrawable(R.mipmap.ic_liked))
                } else {
                    ivLike.setImageDrawable(context.resources.getDrawable(R.mipmap.ic_like))
                }
            } else {
                ivLike.setImageDrawable(context.resources.getDrawable(R.mipmap.ic_like))
            }
            tvLike.text = (activityInfo.like.split(";").size.minus(1)).toString()
        } else {
            ivLike.setImageDrawable(context.resources.getDrawable(R.mipmap.ic_like))
            tvLike.text = "0"
        }
        if (!TextUtils.isEmpty(activityInfo.comment)) {
            tvComment.text = activityInfo.comment?.split(";")?.size?.minus(1).toString()
        } else {
            tvComment.text = "0"
        }

        if (!TextUtils.isEmpty(activityInfo.url)) {
            ivPhoto.adjustViewBounds = true
            Picasso.with(context)
                    .load(activityInfo.url!!.split(";")[0])
//                        .transform(transform)
                    .placeholder(R.mipmap.img_custom)
                    .into(ivPhoto)
        }
        val user = activityInfo.creator
        tvUserName.text = user?.username
        if (!TextUtils.isEmpty(user?.avatarUrl)) {
            Picasso.with(context)
                    .load(user?.avatarUrl)
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
            Log.e(TAG, "itemViewClick")
            val intent = Intent(mActivity, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("activityInfo", updatedActivityInfo)
            bundle.putInt("position", holderPosition)
            intent.putExtras(bundle)
            mActivity.startActivityForResult(intent, IntentRequestCode.MAIN_TO_DETAIL)
        }
        when (view.id) {
            R.id.bt_follow -> {
                Log.e(TAG, "followClick")
            }
            R.id.ll_like -> {
                Log.e(TAG, "likeClick")
                if (currentUser.username!!.isNotEmpty()) {
                    updatedActivityInfo.like = updatedActivityInfo.like + currentUser.objectId + ";"
                    like(activityInfo.objectId!!, updatedActivityInfo.like)
                } else {
                    mActivity.startActivityForResult(Intent(mActivity, LoginActivity::class.java), IntentRequestCode.MAIN_TO_LOGIN)
                    makeShortToast(context, "UserId is null")
                }
            }
            R.id.ll_comment -> {
                Log.e(TAG, "commentClick")
            }
        }
    }

    override fun like(activityId: String, like: String) {

        val map = HashMap<String, String>()
        map.put("like", like)
        val body = Gson().toJson(map)
        Log.e(TAG, body)
        presenter.like(activityId, body)
    }

    override fun cancelLike(activityId: String, like: String) {
        val map = HashMap<String, String>()
        map.put("like", like)
        val body = Gson().toJson(map)
        presenter.cancelLike(activityId, body)
    }

    override fun follow(followUserId: String, followerUserId: String) {
        presenter.follow(followUserId, followerUserId)
    }

    override fun likeSuccess() {
        activityInfo = updatedActivityInfo
        if (comment.activity != null) {
            comment.activity = updatedActivityInfo
            adapter.mList[holderPosition] = comment
        } else {
            adapter.mList[holderPosition] = updatedActivityInfo
        }
        adapter.notifyItemChanged(holderPosition)
        Log.e(TAG, "likeSuccess")
    }

    override fun cancelLikeSuccess() {
        Log.e(TAG, "cancelLikeSuccess")
    }

    override fun followSuccess() {
        Log.e(TAG, "followSuccess")
    }

    override fun fail(errorMsg: String) {
        makeShortToast(context, errorMsg)
    }

    val transform = (object : Transformation {
        override fun transform(source: Bitmap): Bitmap {
            val size = minOf(source.width, source.height)
            val x = (source.width.minus(size)).div(2)
            val y = (source.height.minus(size)).div(2)
            Log.e("transformBefore", "width = ${source.width}, " +
                    "height = ${source.height} , " +
                    "size = $size , " +
                    "x = $x , " +
                    "y = $y")
            val result = Bitmap.createBitmap(source, x, y, size, size)
            if (result != source) {
                source.recycle()
            }
            Log.e("transformAfter", "width = ${result.width}, " +
                    "height = ${result.height} , " +
                    "size = $size")
            return result
        }

        override fun key(): String = "square()"
    })
}