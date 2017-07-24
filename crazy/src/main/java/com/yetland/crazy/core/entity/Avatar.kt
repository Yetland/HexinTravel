package com.yetland.crazy.core.entity


/**
 * @Name:           Avatar
 * @Author:         yeliang
 * @Date:           2017/7/21
 */
class Avatar : BaseEntity() {
    var checked = false
    var avatarUrl: Int = 0
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }
}