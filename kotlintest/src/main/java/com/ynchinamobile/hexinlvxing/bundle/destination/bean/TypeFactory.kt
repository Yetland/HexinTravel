package com.ynchinamobile.hexinlvxing.bundle.destination.bean

import android.view.View
import com.ynchinamobile.hexinlvxing.R
import com.ynchinamobile.hexinlvxing.bundle.destination.holder.CarViewHolder
import com.ynchinamobile.hexinlvxing.bundle.destination.holder.DogViewHolder
import com.ynchinamobile.hexinlvxing.core.base.BaseFooterViewHolder
import com.ynchinamobile.hexinlvxing.core.base.BaseViewHolder

/**
 * @Name:           Visitable
 * @Author:         yeliang
 * @Date:           2017/7/7
 */

interface TypeFactory {
    fun type(duck: Duck): Int
    fun type(mouse: Mouse): Int
    fun type(dog: Dog): Int
    fun type(car: Car): Int
    fun type(footer: Footer): Int

    fun createViewHolder(type: Int, view: View): BaseViewHolder<Visitable>
}

class TypeFactoryForList : TypeFactory {

    override fun type(duck: Duck) = R.layout.item_image
    override fun type(mouse: Mouse) = R.layout.item_top
    override fun type(dog: Dog) = R.layout.item_top
    override fun type(car: Car) = R.layout.item_image
    override fun type(footer: Footer) = R.layout.item_footer

    override fun createViewHolder(type: Int, view: View): BaseViewHolder<Visitable> {
        when (type) {
            R.layout.item_top -> {
                return DogViewHolder(view)
            }
            R.layout.item_image -> {
                return CarViewHolder(view)
            }
            R.layout.item_footer -> {
                return BaseFooterViewHolder(view)
            }
            else -> return DogViewHolder(view)
        }
    }
}