package com.yetland.crazy.bundle.destination.holder

import android.util.Log
import android.view.View
import com.yetland.crazy.bundle.destination.bean.Dog
import com.yetland.crazy.bundle.destination.bean.Visitable
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder

/**
 * @Name:           DogViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/7
 */
class DogViewHolder constructor(view: View) : BaseViewHolder<Visitable>(view) {

    override fun setData(t: Visitable, position: Int, adapter: BaseAdapter<Visitable>) {
        if (t is Dog) {
//            Log.e("DogViewHolder", "I'm Dog. My name is ${t.name} , my age is ${t.age}")
        }
    }
}