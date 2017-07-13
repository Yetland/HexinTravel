package com.yetland.crazy.core.api

import com.yetland.crazy.core.entity.*
import rx.Observable

/**
 * @Name:           AppApi
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
interface AppApi {

    fun login(username: String, password: String): Observable<_User>
    fun register(user: _User): Observable<_User>

    fun getActivities(include: String, skip: Int, limit: Int): Observable<Data<ActivityInfo>>
    fun likeActivity(activityId: String, like: String): Observable<BaseResult>
    fun followUser(followUserId: String, followerUserId: String): Observable<BaseEntity>
    fun getComment(activityPoint: Point, skip: Int, limit: Int): Observable<Data<Comment>>
}