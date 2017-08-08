package com.yetland.crazy.core.entity

/**
 * @Name: Banner
 * @Author: yeliang
 * @Date: 2017/8/7
 */

class BannerEntity : BaseEntity() {

    lateinit var bannerList: ArrayList<Banner>

    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }
}

class Banner : BaseEntity() {


    var desc: String = ""
    var imgUrl: String = ""
    var imgSourceId: Int = -1

    override fun type(typeFactory: TypeFactory): Int {
        return -1
    }
}