package com.ynchinamobile.hexinlvxing.bundle.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ynchinamobile.hexinlvxing.R
import com.ynchinamobile.hexinlvxing.core.base.BaseAdapter
import com.ynchinamobile.hexinlvxing.core.base.BaseViewHolder
import com.ynchinamobile.hexinlvxing.core.entity.ActivityInfo

/**
 * @Name:           MainListAdapter
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class MainListAdapter : BaseAdapter<ActivityInfo>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<ActivityInfo> {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.item_main, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ActivityInfo>?, position: Int) {
        holder?.setData(mList[position], position, this)
        super.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class MainHolder constructor(itemView: View) : BaseViewHolder<ActivityInfo>(itemView) {

        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvContent: TextView = itemView.findViewById(R.id.tv_content)

        override fun setData(t: ActivityInfo, position: Int, adapter: BaseAdapter<ActivityInfo>) {
            tvTitle.text = t.title
            tvContent.text = t.content
        }
    }
}