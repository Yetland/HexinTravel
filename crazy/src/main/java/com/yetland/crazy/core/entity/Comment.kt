package com.yetland.crazy.core.entity

/**
 * @Name: Comment
 * @Author: yeliang
 * @Date: 2017/7/12
 */
open class Comment : BaseEntity() {

    var content: String = ""
    var activity: ActivityInfo = ActivityInfo()
    var creator: _User = _User()
    var activityId: String = ""
    var creatorId: String = ""
    @Transient var type: Int = 0
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }
}

class MyComment : Comment() {
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(MyComment())
    }
}

class CommitComment constructor(val activity: Point, val creator: Point, val content: String) {
    var activityId: String = activity.objectId
    var creatorId: String = creator.objectId
}