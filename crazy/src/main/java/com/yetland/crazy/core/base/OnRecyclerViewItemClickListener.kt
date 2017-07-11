package com.yetland.crazy.core.base

/**
 * @Name:           OnRecyclerViewItemClickListener
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
interface OnRecyclerViewItemClickListener{
    fun onRecyclerViewItemClick(position: Int)
}

interface RecyclerViewListener {
    fun onRefresh()
    fun onLoadMore()
}