package com.yetland.crazy.core.entity

import com.yetland.crazy.bundle.destination.bean.TypeFactory

/**
 * @Name:           ActivityInfo
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

class ActivityInfo : BaseEntity() {
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }

    var title: String? = null// 活动的标题
    var content: String? = null// 活动内容简介
    var creator: User? = null
}