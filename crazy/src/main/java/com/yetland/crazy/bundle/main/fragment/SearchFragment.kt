package com.yetland.crazy.bundle.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yetland.crazy.bundle.main.contract.SearchContract
import com.yetland.crazy.bundle.main.contract.SearchModel
import com.yetland.crazy.bundle.main.contract.SearchPresenter
import com.yetland.crazy.core.base.BaseFragment
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.entity.Banner
import com.yetland.crazy.core.entity.BannerEntity
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : BaseFragment(), SearchContract.View, RecyclerViewListener {


    val presenter = SearchPresenter(SearchModel(), this)
    lateinit var rvSearch: BaseRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (currentView == null) {
            currentView = inflater!!.inflate(R.layout.fragment_search, container, false)
            rvSearch = currentView!!.rvSearch

            rvSearch.canLoadMore = false
            rvSearch.initView(activity)
            rvSearch.recyclerViewListener = this
            rvSearch.onLoading()
            getBanner()
        }
        return currentView
    }

    override fun onRefresh() {
        currentPage = 0
        getBanner()
    }

    override fun onLoadMore() {

    }

    override fun onErrorClick() {
        rvSearch.onLoading()
        getActivity()
    }

    override fun getBanner() {
        presenter.getBanner()
    }

    override fun getBannerSuccess(data: Data<Banner>) {
        val list = ArrayList<BaseEntity>()
        val banner = BannerEntity()
        banner.bannerList = data.results!!
        list.add(banner)
        rvSearch.onDefaultComplete(list, 0)
    }

    override fun getBannerFailed(msg: String) {
        rvSearch.onLoadError(msg)
    }
}
