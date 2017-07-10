package com.ynchinamobile.hexinlvxing.bundle.destination.holder

import android.util.Log
import android.view.View
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.Car
import com.ynchinamobile.hexinlvxing.bundle.destination.bean.Visitable
import com.ynchinamobile.hexinlvxing.core.base.BaseAdapter
import com.ynchinamobile.hexinlvxing.core.base.BaseViewHolder

/**
 * @Name:           CarViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/7
 */
class CarViewHolder constructor(view: View) : BaseViewHolder<Visitable>(view) {

    override fun setData(t: Visitable, position: Int, adapter: BaseAdapter<Visitable>) {
        if (t is Car){
//            Log.e("CarViewHolder", "I'm Car. My brand is ${t.brand}, price is ${t.price}")
        }
    }
}