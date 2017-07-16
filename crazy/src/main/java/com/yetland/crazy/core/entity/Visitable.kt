package com.yetland.crazy.core.entity

/**
 * @Name:           Animal
 * @Author:         yeliang
 * @Date:           2017/7/7
 */

class User : BaseEntity() {
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }

}

class Footer : BaseEntity() {
    var noMore = false
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }
}
