package com.yetland.crazy.bundle.travel.adapter

import android.app.Activity
import android.util.Log
import android.view.View
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder

/**
 * @Name:           TravelTopViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/7
 */
class TravelTopViewHolder<String> constructor(view: View) : BaseViewHolder<String>(view) {

    override fun setData(t: String, position: Int, adapter: BaseAdapter<String>, activity: Activity) {
        Log.e("TravelTopViewHolder", t.toString())
    }
}