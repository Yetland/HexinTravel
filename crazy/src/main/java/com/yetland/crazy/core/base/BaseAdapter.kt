package com.yetland.crazy.core.base

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.yetland.crazy.core.utils.LogUtils
import com.ynchinamobile.hexinlvxing.R

/**
 * @Name:           BaseAdapter
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
open class BaseAdapter<T> constructor(activity: Activity) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var mList = ArrayList<T>()
    val mActivity = activity

    override fun onBindViewHolder(holder: BaseViewHolder<T>?, position: Int) {
        if (holder is BaseItemViewHolder) {
            LogUtils.e("setData : ${mList[position]}")
            holder.setData(mList[position], position, this, mActivity)
        }
        if (onItemClickListener != null && holder != null) {
            holder.itemView.setOnClickListener({
                run {
                    onItemClickListener?.onRecyclerViewItemClick(position)
                }
            })
        }
    }

    override fun getItemCount(): Int {
        LogUtils.e("getItemCount : ${mList.size}")

        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<T> {
        LogUtils.e("onCreateViewHolder")

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_base, parent, false)
        return BaseItemViewHolder(view)
    }

    var onItemClickListener: OnRecyclerViewItemClickListener? = null

}