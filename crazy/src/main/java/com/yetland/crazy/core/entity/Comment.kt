package com.yetland.crazy.core.entity

import com.yetland.crazy.bundle.destination.bean.TypeFactory

/**
 * @Name: Comment
 * @Author: yeliang
 * @Date: 2017/7/12
 */
class Comment : BaseEntity() {

    var content: String? = null
    var activity: ActivityInfo? = null
    var creator: _User? = null

    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }

}