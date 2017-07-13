package com.yetland.crazy.core.entity

import com.yetland.crazy.bundle.destination.bean.TypeFactory
import java.io.Serializable

/**
 * @Name:           BaseEntity
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BaseEntity : Serializable {
    var objectId: String? = ""
    var updatedAt: String? = null
    var createdAt: String? = null
    var url: String? = null
    var clickable = true
    abstract fun type(typeFactory: TypeFactory): Int
}

class BaseResult {
    var objectId: String? = null
    var updateAt: String? = null
}

class Point(className: String, objectId: String) {
    var className: String? = className
    var objectId: String? = objectId

}