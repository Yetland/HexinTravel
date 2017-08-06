package com.yetland.crazy.bundle.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yetland.crazy.core.base.BaseFragment
import com.ynchinamobile.hexinlvxing.R

class SearchFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (currentView == null)
            currentView = inflater!!.inflate(R.layout.fragment_search, container, false)
        return currentView
    }

}
