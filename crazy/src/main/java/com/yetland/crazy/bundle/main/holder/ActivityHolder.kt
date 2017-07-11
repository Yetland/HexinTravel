package com.yetland.crazy.bundle.main.holder

import android.graphics.Bitmap
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
import com.ynchinamobile.hexinlvxing.R
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth



/**
 * @Name:           ActivityHolder
 * @Author:         yeliang
 * @Date:           2017/7/11
 */
class ActivityHolder constructor(itemView: View) : BaseViewHolder<BaseEntity>(itemView) {
    var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    var tvContent: TextView = itemView.findViewById(R.id.tv_content)
    var ivPhoto = itemView.findViewById<ImageView>(R.id.iv_photo)
    var ivAvatar = itemView.findViewById<ImageView>(R.id.iv_avatar)
    var tvUserName = itemView.findViewById<TextView>(R.id.tv_user_name)


    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>) {
        if (t is ActivityInfo) {
            tvTitle.text = t.title
            tvContent.text = t.content
            if (!TextUtils.isEmpty(t.url)) {
                Picasso.with(itemView.context)
                        .load(t.url!!.split(";")[0])
                        .transform(transform)
                        .placeholder(R.mipmap.img_custom)
                        .into(ivPhoto)
            }
            val user = t.creator
            tvUserName.text = user?.username
            if (!TextUtils.isEmpty(user?.avatarUrl)) {
                Picasso.with(itemView.context)
                        .load(user?.avatarUrl)
                        .placeholder(R.mipmap.huas)
                        .into(ivAvatar)
            }
        }
    }

    val transform = (object : Transformation {
        override fun transform(source: Bitmap?): Bitmap {
//            val size = Math.min(source?.width, source?.height)
            val size = 10
//            val x = (source?.width - size) / 2
            val x = 100
//            val y = (source?.height - size) / 2
            val y = 100
            val result = Bitmap.createBitmap(source, x, y, size, size)
            if (result != source) {
                source?.recycle()
            }
            return result
        }

        override fun key(): String = ""
    })
}