package com.yetland.crazy.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yetland.crazy.bundle.destination.bean.TypeFactory
import com.yetland.crazy.bundle.destination.bean.TypeFactoryForList
import com.yetland.crazy.core.entity.BaseEntity

/**
 * @Name:           BaseMultiTypeAdapter
 * @Author:         yeliang
 * @Date:           2017/7/7
 */
class BaseMultiTypeAdapter : BaseAdapter<BaseEntity>() {

    var typeFactory: TypeFactory = TypeFactoryForList()

    override fun onBindViewHolder(holder: BaseViewHolder<BaseEntity>?, position: Int) {
        holder?.setData(mList[position], position, this)
        super.onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<BaseEntity> {
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