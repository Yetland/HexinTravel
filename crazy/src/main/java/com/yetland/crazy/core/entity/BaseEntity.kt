package com.yetland.crazy.core.entity

import com.yetland.crazy.bundle.destination.bean.TypeFactory

/**
 * @Name:           BaseEntity
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BaseEntity {
    var objectId: String? = null
    var updatedAt: String? = null
    var createdAt: String? = null
    var url: String? = null
    abstract fun type(typeFactory: TypeFactory): Int
}