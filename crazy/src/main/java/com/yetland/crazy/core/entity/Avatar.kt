package com.yetland.crazy.core.entity


/**
 * @Name:           Avatar
 * @Author:         yeliang
 * @Date:           2017/7/21
 */
class Avatar : BaseEntity() {
    var checked = false
    var avatarPath: String = ""
    var avatarUrl: Int = 0
    var type: IMAGE_TYPE = IMAGE_TYPE.INT_RES
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }

    enum class IMAGE_TYPE {
        INT_RES,
        STRING_PATH_RES
    }
}