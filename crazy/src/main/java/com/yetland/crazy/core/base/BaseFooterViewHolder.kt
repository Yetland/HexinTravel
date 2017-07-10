package com.yetland.crazy.core.base

import android.view.View
import com.yetland.crazy.bundle.destination.bean.Visitable

/**
 * @Name:           BaseFooterViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/10
 */
class BaseFooterViewHolder constructor(itemView :View) : BaseViewHolder<Visitable>(itemView){
    override fun setData(t: Visitable, position: Int, adapter: BaseAdapter<Visitable>) {

    }
}