package com.yetland.crazy.bundle.main.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.entity.ActivityInfo
import com.ynchinamobile.hexinlvxing.R

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
        var ivPhoto = itemView.findViewById<ImageView>(R.id.iv_photo)
        override fun setData(t: ActivityInfo, position: Int, adapter: BaseAdapter<ActivityInfo>) {
            tvTitle.text = t.title
            tvContent.text = t.content
            if (!TextUtils.isEmpty(t.url)){
                Picasso.with(itemView.context)
                        .load(t.url!!.split(";")[0])
                        .placeholder(R.mipmap.img_custom)
                        .into(ivPhoto)
            }
        }
    }
}