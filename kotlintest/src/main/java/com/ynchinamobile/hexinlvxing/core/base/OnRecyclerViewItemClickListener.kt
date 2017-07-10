package com.ynchinamobile.hexinlvxing.core.base

/**
 * @Name:           OnRecyclerViewItemClickListener
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
interface OnRecyclerViewItemClickListener{
    fun onRecyclerViewItemClick(position: Int)
}

interface OnRecyclerViewListener {
    fun onRefresh()
    fun onLoadMore()
}