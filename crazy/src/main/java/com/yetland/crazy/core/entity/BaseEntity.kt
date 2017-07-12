package com.yetland.crazy.core.entity

import com.yetland.crazy.bundle.destination.bean.TypeFactory
import java.io.Serializable

/**
 * @Name:           BaseEntity
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BaseEntity : Serializable {
    var objectId: String? = null
    var updatedAt: String? = null
    var createdAt: String? = null
    var url: String? = null
    abstract fun type(typeFactory: TypeFactory): Int
}

class BaseResult {
    var objectId: String? = null
    var updateAt: String? = null
}