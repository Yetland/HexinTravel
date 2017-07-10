package com.ynchinamobile.hexinlvxing.core.base

import android.util.SparseArray
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * @Name:           BaseViewHolder2
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BaseViewHolder2<in T>(val mItemView: View) : RecyclerView.ViewHolder(mItemView) {
    private val views: SparseArray<View> = SparseArray()

    fun getView(resID: Int): View {
        var view: View? = views.get(resID)

        if (view == null) {
            view = mItemView.findViewById(resID)
            views.put(resID, view)
        }

        return view!!
    }

    abstract fun setUpView(model: T, position: Int)
}