package com.yetland.crazy.core.entity

import com.google.gson.annotations.Expose
import com.yetland.crazy.bundle.destination.bean.TypeFactory

/**
 * @Name: Comment
 * @Author: yeliang
 * @Date: 2017/7/12
 */
open class Comment : BaseEntity() {

    var content: String? = null
    var activity: ActivityInfo? = null
    var creator: _User? = null
    @Transient var type: Int = 0
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }
}

class MyComment : Comment(){
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(MyComment())
    }
}