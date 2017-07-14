package com.yetland.crazy.core.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.utils.makeShortToast
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.layout_error.view.*

/**
 * @Name:           BaseRecyclerView
 * @Author:         yeliang
 * @Date:           2017/7/10
 */
class BaseRecyclerView constructor(context: Context, att: AttributeSet) : LinearLayout(context, att), View.OnClickListener {

    var canRefresh = true
    var canLoadMore = true
    var isLoadingMore = false
    var isRefreshing = false

    var layoutError: LinearLayout
    var layoutLoading: LinearLayout
    var errorImage: ImageView
    var errorMessage: TextView

    var layoutContent: LinearLayout
    var layoutManager: RecyclerView.LayoutManager
    var swipeRefreshLayout: SwipeRefreshLayout
    var recyclerView: RecyclerView
    lateinit var adapter: BaseMultiTypeAdapter
    var recyclerViewListener: RecyclerViewListener? = null

    var loadType = 0

    val TYPE_LOADING = 0
    val TYPE_REFRESH = 1
    val TYPE_LOAD_MORE = 2

    init {
        @SuppressLint("InflateParams")
        val layout = LayoutInflater.from(context).inflate(R.layout.layout_base_recyclerview, null)
        layout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutContent = layout.findViewById(R.id.ll_content)
        swipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout)
        recyclerView = layout.findViewById(R.id.recyclerView)
        layoutError = layout.findViewById(R.id.ll_error)
        layoutLoading = layout.findViewById(R.id.ll_loading)
        errorMessage = layout.findViewById(R.id.tv_error_msg)
        errorImage = layout.findViewById(R.id.iv_error_img)

        layoutManager = LinearLayoutManager(context)
        (layoutManager as LinearLayoutManager).orientation = LinearLayout.VERTICAL

        addView(layout)
    }

    fun initView(activity: Activity) {
        adapter = BaseMultiTypeAdapter(activity)
        recyclerView.layoutManager = layoutManager
        swipeRefreshLayout.setOnRefreshListener {
            if (canRefresh && !isRefreshing && !isLoadingMore) {
                loadType = TYPE_REFRESH
                canRefresh = false
                isRefreshing = true
                recyclerViewListener?.onRefresh()
            } else {
                swipeRefreshLayout.isRefreshing = false
            }
        }
        if (canLoadMore) {
            recyclerView.addOnScrollListener((object : RecyclerView.OnScrollListener() {
                var lastVisibleItem: Int = 0
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (canLoadMore && lastVisibleItem.plus(1) == adapter.itemCount) {

                        if (!isRefreshing && !isLoadingMore) {
                            loadType = TYPE_LOAD_MORE
                            canRefresh = false
                            isLoadingMore = true
                            recyclerViewListener?.onLoadMore()
                        }
                    }
                }
            }))
        }

        recyclerView.layoutManager = layoutManager

    }

    fun onComplete(list: ArrayList<BaseEntity>, noMoreData: Boolean = false) {

        layoutLoading.visibility = View.GONE
        layoutError.visibility = View.GONE
        layoutContent.visibility = View.VISIBLE
        if (adapter.itemCount == 0) {
            recyclerView.adapter = adapter
        }
        adapter.mList = list
        swipeRefreshLayout.isRefreshing = false
        canRefresh = true
        canLoadMore = !noMoreData
        isLoadingMore = false
        isRefreshing = false
        adapter.notifyDataSetChanged()
    }

    fun onLoading() {
        layoutLoading.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
        layoutContent.visibility = View.GONE
    }

    fun onLoadError(errorMsg: String = "加载失败啦~") {

        swipeRefreshLayout.isRefreshing = false
        canRefresh = true
        isLoadingMore = false
        isRefreshing = false

        when (loadType) {
            TYPE_LOADING -> {
                layoutLoading.visibility = View.GONE
                layoutError.visibility = View.VISIBLE
                layoutContent.visibility = View.GONE

                errorMessage.text = errorMsg
                errorImage.setOnClickListener(this)
            }
            TYPE_REFRESH -> onRefreshError(errorMsg)
            TYPE_LOAD_MORE -> onLoadMoreError(errorMsg)
        }

    }

    private fun onRefreshError(errorMsg: String) {
        makeShortToast(context, errorMsg)
    }

    private fun onLoadMoreError(errorMsg: String) {

        adapter.mList.removeAt(adapter.mList.size - 1)
        adapter.notifyDataSetChanged()
        makeShortToast(context, errorMsg)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_error_img -> {
                recyclerViewListener?.onErrorClick()
            }
        }
    }

}
