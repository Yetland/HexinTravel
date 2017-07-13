package com.yetland.crazy.bundle.travel.adapter

import android.app.Activity
import android.util.Log
import android.view.View
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder

/**
 * @Name:           TravelImageViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/7
 */
class TravelImageViewHolder<String> constructor(view: View) : BaseViewHolder<String>(view) {

    override fun setData(t: String, position: Int, adapter: BaseAdapter<String>, activity: Activity) {
        Log.e("TravelImageViewHolder", t.toString())
        itemView.setOnClickListener {

        }
    }
}