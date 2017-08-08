package com.yetland.crazy.bundle.main.adapter

import android.app.Activity
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yetland.crazy.core.entity.Banner
import com.yetland.crazy.core.utils.LogUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.item_banner_image.view.*

/**
 * @Name: BannerAdapter
 * @Author: yeliang
 * @Date: 2017/8/7
 */
class BannerAdapter constructor(activity: Activity, list: ArrayList<Banner>) : PagerAdapter() {

    val mActivity = activity
    val mLIst = list
    lateinit var currentView: View
    lateinit var ivBanner: ImageView
    lateinit var tvDesc: TextView

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        currentView = LayoutInflater.from(mActivity).inflate(R.layout.item_banner_image, container, false)
        val banner = mLIst[position]

        ivBanner = currentView.ivBanner
        tvDesc = currentView.tvDesc

        tvDesc.text = banner.desc

        if (banner.imgUrl.isEmpty()) {
            ivBanner.tag = banner.imgSourceId
            Picasso.with(mActivity)
                    .load(banner.imgSourceId)
                    .placeholder(R.mipmap.image_load_16_9)
                    .into(ivBanner)
        } else {
            ivBanner.tag = banner.imgUrl
            Picasso.with(mActivity)
                    .load(banner.imgUrl)
                    .placeholder(R.mipmap.image_load_16_9)
                    .into(ivBanner)
        }

        container?.addView(currentView, 0)
        return currentView
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {

        return mLIst.size
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        LogUtils.e("destroyItem", "destroyItem -> $position")
        container?.removeView(`object` as View?)
    }
}