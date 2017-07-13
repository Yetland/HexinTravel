package com.yetland.crazy.core.base

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @Name:           BaseViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

abstract class BaseViewHolder<T> constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var context: Context = itemView.context
    lateinit var mActivity: Activity
    abstract fun setData(t: T, position: Int, adapter: BaseAdapter<T>, activity: Activity)
}