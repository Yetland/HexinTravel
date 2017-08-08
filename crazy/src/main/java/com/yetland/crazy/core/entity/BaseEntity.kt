package com.yetland.crazy.core.entity

import java.io.Serializable

/**
 * @Name:           BaseEntity
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BaseEntity : Serializable {
    /**
     * get() {
    var time = createdAt
    time.replace("T", " ")
    time.replace("Z", "")
    time = time.split(".")[0]

    return time
    }
     */
    var objectId: String = ""
    var updatedAt: String? = null
    var createdAt: String = ""

    var url: String = ""
    @Transient var clickable = true
    abstract fun type(typeFactory: TypeFactory): Int
}

class BaseResult {
    var objectId: String = ""
    var updateAt: String = ""
    var url: String? = null
}

class Point(var className: String, var objectId: String) {
    val __type = "Pointer"
}

class Op {
    var __op: String = ""
    var amount: Int = 0
}

class Dot {
    var selected = false
}