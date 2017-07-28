package com.yetland.crazy.core.api

import com.yetland.crazy.core.entity.*
import rx.Observable
import java.io.File

/**
 * @Name:           AppApi
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
interface AppApi {

    fun getUser(objectId: String): Observable<_User>
    fun login(username: String, password: String): Observable<_User>
    fun register(user: _User): Observable<_User>

    fun getActivities(include: String, where: String?, skip: Int, limit: Int): Observable<Data<ActivityInfo>>
    fun updateActivity(activityId: String, where: String): Observable<BaseResult>
    fun followUser(followUserId: String, followerUserId: String): Observable<BaseEntity>
    fun getComment(map: HashMap<String, String>, skip: Int, limit: Int): Observable<Data<Comment>>
    fun getMyComment(map: HashMap<String, String>, skip: Int, limit: Int): Observable<Data<MyComment>>
    fun writeComment(comment: CommitComment): Observable<BaseResult>

    fun getFollow(where: String, include: String, order: String, skip: Int, limit: Int): Observable<Data<Follow>>
    fun follow(follow: CommitFollow): Observable<Follow>
    fun unFollow(objectId: String): Observable<BaseResult>

    fun compressImage(file: File): Observable<File>
    fun uploadImage(file: File): Observable<BaseResult>
}