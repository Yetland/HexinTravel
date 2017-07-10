package com.ynchinamobile.hexinlvxing.bundle.destination.holder

import android.view.View
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.Mouse
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.Visitable
import com.ynchinamobile.hexinlvxing.core.base.BaseAdapter
import com.ynchinamobile.hexinlvxing.core.base.BaseViewHolder

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