package com.yetland.crazy.bundle.user.holder

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.entity.Avatar
import com.yetland.crazy.core.entity.BaseEntity
import com.ynchinamobile.hexinlvxing.R

/**
 * @Name:           AvatarHolder
 * @Author:         yeliang
 * @Date:           2017/7/21
 */
class AvatarHolder constructor(view: View) : BaseViewHolder<BaseEntity>(view) {
    var avatarId = listOf(R.mipmap.ic_avatar_1, R.mipmap.ic_avatar_2,
            R.mipmap.ic_avatar_3, R.mipmap.ic_avatar_4,
            R.mipmap.ic_avatar_5, R.mipmap.ic_avatar_6,
            R.mipmap.ic_avatar_7, R.mipmap.ic_avatar_8,
            R.mipmap.ic_avatar_9, R.mipmap.ic_avatar_10,
            R.mipmap.ic_avatar_11, R.mipmap.ic_avatar_12,
            R.mipmap.ic_avatar_13, R.mipmap.ic_avatar_14,
            R.mipmap.ic_avatar_15, R.mipmap.ic_avatar_16,
            R.mipmap.ic_avatar_17, R.mipmap.ic_avatar_18,
            R.mipmap.ic_avatar_19, R.mipmap.ic_avatar_20,
            R.mipmap.ic_avatar_21, R.mipmap.ic_avatar_22,
            R.mipmap.ic_avatar_23, R.mipmap.ic_avatar_24,
            R.mipmap.ic_avatar_25, R.mipmap.ic_avatar_26,
            R.mipmap.ic_avatar_27, R.mipmap.ic_avatar_28,
            R.mipmap.ic_avatar_29, R.mipmap.ic_avatar_30,
            R.mipmap.ic_avatar_31, R.mipmap.ic_avatar_32)

    var avatar = Avatar()
    var ivAvatar = view.findViewById<ImageView>(R.id.iv_user_avatar)

    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>, activity: Activity) {
        if (t is Avatar) {
            avatar = t
//            if (avatar.avatarUrl != 0) {
//                Picasso.with(context)
//                        .load(R.mipmap.ic_avatar_13)
//                        .placeholder(R.mipmap.ic_avatar_1)
//                        .into(ivAvatar)
//            }
        }

    }
}