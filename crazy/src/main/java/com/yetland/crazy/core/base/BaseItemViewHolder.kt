package com.yetland.crazy.core.base

import android.view.View
import android.widget.TextView
import com.ynchinamobile.hexinlvxing.R

/**
 * @Name:           BaseItemViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/10
 */
class BaseItemViewHolder<T> constructor(itemView: View) : BaseViewHolder<T>(itemView) {
    val textView = itemView.findViewById<TextView>(R.id.textView)!!
    override fun setData(t: T, position: Int, adapter: BaseAdapter<T>) {
        if (t is String) {
            textView.text = t
        }
    }
}