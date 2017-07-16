package com.yetland.crazy.bundle.main.holder

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Comment
import com.ynchinamobile.hexinlvxing.R

/**
 * @Name: CommentHolder
 * @Author: yeliang
 * @Date: 2017/7/12
 */
class CommentHolder constructor(view: View) : BaseViewHolder<BaseEntity>(view) {


    lateinit var comment: Comment
    val tvUsername: TextView = view.findViewById(R.id.tv_name)
    val ivAvatar: ImageView = view.findViewById(R.id.iv_comment_avatar)
    var tvCommentContent: TextView = view.findViewById(R.id.tv_comment_content)
    var tvCommentTime: TextView = view.findViewById(R.id.tv_comment_time)
    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>, activity: Activity) {

        if (t is Comment) {
            comment = t

            tvCommentContent.text = comment.content
            tvCommentTime.text = comment.createdAt
            val creator = comment.creator
            tvUsername.text = creator.username

            if (creator.avatarUrl!!.isNotEmpty()){

                Picasso.with(context)
                        .load(creator.avatarUrl)
                        .placeholder(R.mipmap.huas)
                        .into(ivAvatar)
            }
        }
        /* if (itemView != null) {
             val tvUsername = itemView.findViewById<TextView>(R.id.tv_user_name)!!
             val ivAvatar = itemView.findViewById<ImageView>(R.id.iv_avatar)!!
             var tvCommentContent: TextView = itemView.findViewById(R.id.tv_comment_content)
             var tvCommentTime: TextView = itemView.findViewById(R.id.tv_comment_time)
             if (t is Comment) {
                 comment = t

             }
         }else{
             makeShortToast(context,"View is null")
         }*/
    }

}
