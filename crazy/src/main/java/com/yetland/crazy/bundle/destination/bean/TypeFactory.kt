package com.yetland.crazy.bundle.destination.bean

import android.view.View
import com.yetland.crazy.bundle.main.holder.ActivityHolder
import com.yetland.crazy.core.base.BaseFooterViewHolder
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
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
}

class TypeFactoryForList : TypeFactory {
    override fun type(user: User): Int = R.layout.item_main_activity
    override fun type(activityInfo: ActivityInfo): Int = R.layout.item_main_activity
    override fun type(footer: Footer) = R.layout.item_footer

    override fun createViewHolder(type: Int, view: View): BaseViewHolder<BaseEntity> {
        when (type) {

            R.layout.item_main_activity -> {
                return ActivityHolder(view)
            }
            R.layout.item_footer -> {
                return BaseFooterViewHolder(view)
            }
            else -> return BaseFooterViewHolder(view)
        }
    }
}