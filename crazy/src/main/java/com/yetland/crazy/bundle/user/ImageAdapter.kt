package com.yetland.crazy.bundle.user

import android.app.Activity
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.yetland.crazy.core.utils.LogUtils
import com.ynchinamobile.hexinlvxing.R
import uk.co.senab.photoview.PhotoView
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import com.yetland.crazy.core.utils.FileUtils
import com.yetland.crazy.core.utils.ImageUtils
import com.yetland.crazy.core.utils.ToastUtils


/**
 * @Name:           ImageAdapter
 * @Author:         yeliang
 * @Date:           2017/8/2
 */
class ImageAdapter constructor(activity: Activity, urls: ArrayList<String>) : PagerAdapter() {

    var mUrls = urls
    var mActivity = activity
    var bitmap: Bitmap? = null

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mUrls.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {

        val iv = PhotoView(mActivity)
        val imgUrl = mUrls[position]
        if (imgUrl.isNotEmpty()) {
            Picasso.with(mActivity)
                    .load(imgUrl)
                    .placeholder(R.mipmap.img_custom)
                    .into(iv)
        }

        iv.setOnClickListener({
            mActivity.finish()
        })

        container?.addView(iv, position)
        return iv
    }

    private fun download(imgUrl: String) {
        //Target
        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                FileUtils.createOrExistsDir(ImageUtils.compressImage(bitmap))
            }

            override fun onBitmapFailed(errorDrawable: Drawable) {
                ToastUtils.showShortSafe("SaveFailed")
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable) {

            }
        }

        //Picasso下载
        Picasso.with(mActivity).load(imgUrl).into(target)
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        LogUtils.e("destroyItem", "destroyItem -> $position")
        container?.removeViewAt(position)
    }
}