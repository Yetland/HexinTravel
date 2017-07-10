package com.ynchinamobile.hexinlvxing.bundle.travel.adapter

import android.util.Log
import android.view.View
import com.ynchinamobile.hexinlvxing.core.base.BaseAdapter
import com.ynchinamobile.hexinlvxing.core.base.BaseViewHolder

/**
 * @Name:           TravelTopViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/7
 */
class TravelTopViewHolder<String> constructor(view: View) : BaseViewHolder<String>(view) {

    override fun setData(t: String, position: Int, adapter: BaseAdapter<String>) {
        Log.e("TravelTopViewHolder", t.toString())
    }
}