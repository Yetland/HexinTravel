package com.yetland.crazy.bundle.user

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @Name:           UserDetailAdapter
 * @Author:         yeliang
 * @Date:           2017/7/20
 */
class UserDetailAdapter constructor(fragmentManager: FragmentManager,
                                    list: ArrayList<Fragment>,
                                    titleList: ArrayList<String>) : FragmentPagerAdapter(fragmentManager) {


    var list = ArrayList<Fragment>()
    var titleList = ArrayList<String>()

    init {
        this.list = list
        this.titleList = titleList
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }
}