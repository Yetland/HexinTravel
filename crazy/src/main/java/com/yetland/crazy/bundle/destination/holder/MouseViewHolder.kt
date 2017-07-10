package com.yetland.crazy.bundle.destination.holder

import android.view.View
import com.yetland.crazy.bundle.destination.bean.Mouse
import com.yetland.crazy.bundle.destination.bean.Visitable
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder

/**
 * @Name:           MouseViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/7
 */
class MouseViewHolder constructor(view : View): BaseViewHolder<Visitable>(view){

    override fun setData(t: Visitable, position: Int, adapter: BaseAdapter<Visitable>) {
        if (t is Mouse){

        }
    }
}