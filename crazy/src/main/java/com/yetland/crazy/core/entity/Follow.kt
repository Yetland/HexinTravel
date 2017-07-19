package com.yetland.crazy.core.entity

import java.io.Serializable

/**
 * @Name: Follow
 * @Author: yeliang
 * @Date: 2017/7/16
 */
class Follow : BaseEntity() {

    var follower = _User()
    var user = _User()

    @Transient var isFollower = true
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }
}

class CommitFollow : Serializable{
    var follower: Point? = null
    var user: Point? = null
}

