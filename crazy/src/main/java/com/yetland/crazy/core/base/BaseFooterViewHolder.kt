package com.yetland.crazy.core.base

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.yetland.crazy.bundle.destination.bean.Footer
import com.yetland.crazy.core.entity.BaseEntity
import com.ynchinamobile.hexinlvxing.R

/**
 * @Name:           BaseFooterViewHolder
 * @Author:         yeliang
 * @Date:           2017/7/10
 */
class BaseFooterViewHolder constructor(itemView: View) : BaseViewHolder<BaseEntity>(itemView) {

    val llLoading = itemView.findViewById<LinearLayout>(R.id.ll_footer_loading)
    val tvNoMore = itemView.findViewById<TextView>(R.id.tv_no_more)

    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>) {
        if (t is Footer) {
            if (t.noMore) {
                noMoreData()
            } else {
                llLoading.visibility = View.VISIBLE
                tvNoMore.visibility = View.GONE
            }
        }
    }

    fun noMoreData() {
        llLoading.visibility = View.GONE
        tvNoMore.visibility = View.VISIBLE
    }
}