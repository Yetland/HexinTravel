package com.ynchinamobile.hexinlvxing.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ynchinamobile.hexinlvxing.R
import com.ynchinamobile.hexinlvxing.R.id.swipeRefreshLayout

/**
 * @Name:           BaseRecyclerView
 * @Author:         yeliang
 * @Date:           2017/7/10
 */
class BaseRecyclerView<T> constructor(context: Context, att: AttributeSet) : LinearLayout(context, att) {

    var canRefresh = true
    var canLoadMore = true
    var isLoadingMore = false
    var isRefreshing = false


    var layoutManager: RecyclerView.LayoutManager
    var swipeRefreshLayout: SwipeRefreshLayout
    var recyclerView: RecyclerView
    //    var adapter = BaseAdapter<T>()
    var adapter = BaseMultiTypeAdapter()
    var recyclerViewListener: OnRecyclerViewListener? = null

    init {
        @SuppressLint("InflateParams")
        val layout = LayoutInflater.from(context).inflate(R.layout.layout_base_recyclerview, null)
        layout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        swipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout)
        recyclerView = layout.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(context)
        (layoutManager as LinearLayoutManager).orientation = LinearLayout.VERTICAL

        addView(layout)
        initView()
    }

    private fun initView() {
        recyclerView.layoutManager = layoutManager
        swipeRefreshLayout.setOnRefreshListener {
            if (canRefresh && !isRefreshing && !isLoadingMore) {
                canRefresh = false
                isRefreshing = true
                recyclerViewListener?.onRefresh()
            } else {
                swipeRefreshLayout.isRefreshing = false
            }
        }
        if (canLoadMore) {
            recyclerView.addOnScrollListener((object : RecyclerView.OnScrollListener() {
                var lastVisibleItem: Int? = null
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItem?.plus(1)) == adapter.itemCount) {
                        if (!isRefreshing && !isLoadingMore) {

                            canRefresh = false
                            isLoadingMore = true
                            recyclerViewListener?.onLoadMore()
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                }
            }))
        }
        recyclerView.layoutManager = layoutManager
    }

    fun onComplete() {
        swipeRefreshLayout.isRefreshing = false
        canRefresh = true
        canLoadMore = true
        isLoadingMore = false
        isRefreshing = false
        Log.e("BaseRecyclerView", "itemCount : " + adapter.itemCount)
        adapter.notifyDataSetChanged()
    }
}
