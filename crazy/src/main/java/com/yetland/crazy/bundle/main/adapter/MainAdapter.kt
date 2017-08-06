package com.yetland.crazy.bundle.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @Name: MainAdapter
 * @Author: yeliang
 * @Date: 2017/8/4
 */
class MainAdapter(fm: FragmentManager, list: List<Fragment>) : FragmentPagerAdapter(fm) {

    var mList = list

    override fun getItem(position: Int): Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

}