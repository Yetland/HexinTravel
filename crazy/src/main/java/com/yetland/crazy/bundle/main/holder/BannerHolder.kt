package com.yetland.crazy.bundle.main.holder

import android.app.Activity
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.yetland.crazy.bundle.main.adapter.BannerAdapter
import com.yetland.crazy.core.base.BaseAdapter
import com.yetland.crazy.core.base.BaseViewHolder
import com.yetland.crazy.core.entity.Banner
import com.yetland.crazy.core.entity.BannerEntity
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Dot
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.ScreenUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.item_banner.view.*

/**
 * @Name: BannerHolder
 * @Author: yeliang
 * @Date: 2017/8/7
 */
class BannerHolder constructor(itemView: View) : BaseViewHolder<BaseEntity>(itemView) {

    lateinit var banner: BannerEntity
    var bannerList = ArrayList<Banner>()
    var dotList = ArrayList<Dot>()
    lateinit var bannerAdapter: BannerAdapter
    var bannerPosition = -1

    val vpBanner = itemView.vpBanner
    val llDot = itemView.llDot

    override fun setData(t: BaseEntity, position: Int, adapter: BaseAdapter<BaseEntity>, activity: Activity) {
        mActivity = activity
        if (t is BannerEntity) {
            banner = t

            val width = ScreenUtils.getScreenWidth()
            val height = width * 9 / 16
            val params = RelativeLayout.LayoutParams(width, height)
            vpBanner.layoutParams = params

            bannerList.clear()
            dotList.clear()

            bannerList.add(0, banner.bannerList[banner.bannerList.size - 1])

            for (b in banner.bannerList) {
                bannerList.add(b)
                val dot = Dot()
                dotList.add(dot)
            }
            setDot(dotList.size, 0)
            bannerList.add(banner.bannerList[0])
            bannerAdapter = BannerAdapter(activity, bannerList)
            vpBanner.adapter = bannerAdapter
            vpBanner.setCurrentItem(1, false)
            vpBanner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    if (state == 0) {
                        if (bannerPosition == 0) {
                            vpBanner.setCurrentItem(bannerList.size - 2, false)
                            setDot(bannerList.size - 2, bannerList.size - 3)
                        } else if (bannerPosition == bannerList.size - 1) {
                            vpBanner.setCurrentItem(1, false)
                            setDot(bannerList.size - 2, 0)
                        } else {
                            setDot(bannerList.size - 2, bannerPosition - 1)
                        }
                    }
                    LogUtils.e("OnPageChangeListener", "state -> $state")
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    LogUtils.e("OnPageChangeListener", "onPageScrolled,position -> $position,positionOffset -> $positionOffset,positionOffsetPixels -> $positionOffsetPixels")
                }

                override fun onPageSelected(position: Int) {
                    LogUtils.e("OnPageChangeListener", "onPageSelected , position -> $position")
                    bannerPosition = position

                }

            })
        }
    }


    private fun setDot(imageSize: Int, position: Int) {

        llDot.removeAllViews()
        for (i in 0..imageSize - 1) {

            val params = LinearLayout.LayoutParams(20, 20)
            params.rightMargin = 8
            val imgDot = ImageView(mActivity)
            imgDot.setImageResource(R.mipmap.ic_dot)
            imgDot.scaleType = ImageView.ScaleType.FIT_XY
            if (position == i) {
                imgDot.isSelected = true
                imgDot.setImageResource(R.mipmap.ic_dot_selected)
            }
            llDot.addView(imgDot, i, params)
        }
    }
}