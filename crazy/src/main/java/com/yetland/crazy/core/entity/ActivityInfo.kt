package com.yetland.crazy.core.entity

/**
 * @Name:           ActivityInfo
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

class ActivityInfo : BaseEntity() {
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }

    var title: String = ""// 活动的标题
    var content: String? = null// 活动内容简介
    var creator: _User = _User()
    var comment: String = ""
    var like: String = ""
    var likeCount: Int = 0
    var commentCount: Int = 0
    var forwardCount: Int = 0
    var forwardActivity: ActivityInfo? = null
    var forward = false
}

class CreateActivityInfo {
    var forwardActivity: Point? = null
    lateinit var creator: Point
    var title: String = ""
    var url: String = ""
    var forward = false
}