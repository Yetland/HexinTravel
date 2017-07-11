package com.yetland.crazy.core.api

import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.User
import rx.Observable

/**
 * @Name:           AppApi
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
interface AppApi {

    fun login(username: String, password: String): Observable<User>
    fun register(user: User): Observable<BaseEntity>

    fun getActivities(include: String, skip: Int, limit: Int): Observable<Data<ActivityInfo>>
}