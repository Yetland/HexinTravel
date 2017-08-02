package com.yetland.crazy.bundle.user.holder

import android.app.Activity
import android.view.View
import com.squareup.picasso.Picasso
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.entity.Avatar
import com.yetland.crazy.core.entity.BaseEntity
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.item_user_avatar.view.*
import java.io.File

/**
 * @Name:           AvatarHolder
 * @Author:         yeliang
 * @Date:           2017/7/21
 */
class AvatarHolder constructor(view: View) : BaseViewHolder<BaseEntity>(view) {

    var avatar = Avatar()
    var ivAvatar = view.iv_user_avatar
    var cbAvatar = view.cb_user_avatar
    var ivRemove = view.iv_remove

    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>, activity: Activity) {
        if (t is Avatar) {
            avatar = t
            when (t.type) {
                Avatar.IMAGE_TYPE.INT_RES -> {
                    if (avatar.avatarUrl != 0) {
                        Picasso.with(context)
                                .load(avatar.avatarUrl)
                                .placeholder(R.mipmap.ic_avatar_1)
                                .into(ivAvatar)
                    }
                }
                Avatar.IMAGE_TYPE.STRING_PATH_RES -> {
                    if (avatar.avatarPath.isNotEmpty()) {
                        Picasso.with(context)
                                .load(File(avatar.avatarPath))
                                .placeholder(R.mipmap.ic_avatar_1)
                                .resize(200, 200)
                                .centerCrop()
                                .into(ivAvatar)
                    }
                }
            }

            when (t.openType) {
                Avatar.OPEN_TYPE.CHOOSE_IMAGE -> {
                    ivRemove.visibility = View.GONE
                }
                Avatar.OPEN_TYPE.CREATE_ACTIVITY -> {
                    ivRemove.visibility = View.VISIBLE
                    ivRemove.setOnClickListener({
                        adapter.mList.removeAt(position)
                        adapter.notifyDataSetChanged()
                    })
                }
            }

            if (avatar.checked) {
                cbAvatar.visibility = View.VISIBLE
                cbAvatar.isChecked = true
            } else {
                cbAvatar.visibility = View.GONE
            }
        }
    }
}