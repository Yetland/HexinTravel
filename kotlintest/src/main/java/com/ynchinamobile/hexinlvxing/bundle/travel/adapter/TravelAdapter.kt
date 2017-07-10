package com.ynchinamobile.hexinlvxing.bundle.travel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ynchinamobile.hexinlvxing.R
import com.ynchinamobile.hexinlvxing.core.base.BaseAdapter
import com.ynchinamobile.hexinlvxing.core.base.BaseViewHolder

/**
 * @Name:           TravelAdapter
 * @Author:         yeliang
 * @Date:           2017/7/7
 */
class TravelAdapter<String> : BaseAdapter<String>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<String> {
        val view: View
        when (viewType) {
            1 -> {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.item_top, parent, false)
                return TravelTopViewHolder(view)
            }
            2 -> {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.item_image, parent, false)
                return TravelImageViewHolder(view)
            }
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<String>?, position: Int) {
        holder?.setData(mList?.get(position)!! , position , this)
        super.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return mList?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        if (position % 2 == 0) {
            return 1
        } else {
            return 2
        }
    }
}