package com.yetland.crazy.core.entity

import android.view.View
import com.yetland.crazy.bundle.main.holder.ActivityHolder
import com.yetland.crazy.bundle.main.holder.BannerHolder
import com.yetland.crazy.bundle.main.holder.CommentHolder
import com.yetland.crazy.bundle.user.holder.AvatarHolder
import com.yetland.crazy.bundle.user.holder.FollowHolder
import com.yetland.crazy.core.base.BaseFooterViewHolder
import com.yetland.crazy.core.base.BaseViewHolder
import com.ynchinamobile.hexinlvxing.R

/**
 * @Name:           Visitable
 * @Author:         yeliang
 * @Date:           2017/7/7
 */

interface TypeFactory {
    fun createViewHolder(type: Int, view: View): BaseViewHolder<BaseEntity>
    fun type(footer: Footer): Int
    fun type(activityInfo: ActivityInfo): Int
    fun type(user: User): Int
    fun type(comment: Comment): Int
    fun type(follower: Follow): Int
    fun type(avatar: Avatar): Int
    fun type(banner: BannerEntity): Int
}

class TypeFactoryForList : TypeFactory {
    override fun type(banner: BannerEntity): Int {
        return R.layout.item_banner
    }

    override fun type(avatar: Avatar): Int {
        return R.layout.item_user_avatar
    }

    override fun type(follower: Follow): Int {
        return R.layout.item_follower
    }

    override fun type(comment: Comment): Int {
        if (comment is MyComment) {
            return R.layout.item_main_activity
        } else return R.layout.item_comment
    }

    override fun type(user: User): Int = R.layout.item_main_activity
    override fun type(activityInfo: ActivityInfo): Int = R.layout.item_main_activity
    override fun type(footer: Footer) = R.layout.item_footer

    override fun createViewHolder(type: Int, view: View): BaseViewHolder<BaseEntity> {
        when (type) {

            R.layout.item_main_activity -> return ActivityHolder(view)

            R.layout.item_footer -> return BaseFooterViewHolder(view)

            R.layout.item_comment -> return CommentHolder(view)

            R.layout.item_follower -> return FollowHolder(view)

            R.layout.item_user_avatar -> return AvatarHolder(view)

            R.layout.item_banner -> return BannerHolder(view)
            else -> return BaseFooterViewHolder(view)
        }
    }
}