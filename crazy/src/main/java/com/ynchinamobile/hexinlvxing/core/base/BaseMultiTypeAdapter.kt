package com.ynchinamobile.hexinlvxing.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.TypeFactory
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.TypeFactoryForList
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.Visitable
import com.ynchinamobile.hexinlvxing.core.base.BaseAdapter
import com.ynchinamobile.hexinlvxing.core.base.BaseViewHolder

/**
 * @Name:           BaseMultiTypeAdapter
 * @Author:         yeliang
 * @Date:           2017/7/7
 */
class BaseMultiTypeAdapter : BaseAdapter<Visitable>() {

    var typeFactory: TypeFactory = TypeFactoryForList()

    override fun onBindViewHolder(holder: BaseViewHolder<Visitable>?, position: Int) {
        holder?.setData(mList[position], position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<Visitable> {
        val view: View = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)

        return typeFactory.createViewHolder(viewType, view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return mList[position].type(typeFactory)
    }
}