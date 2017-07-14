package com.yetland.crazy.core.api

import com.google.gson.Gson
import com.yetland.crazy.core.entity.*
import okhttp3.RequestBody
import rx.Observable
import rx.Subscriber
import java.io.IOException

/**
 * @Name:           AppApiImpl
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class AppApiImpl : AppApi {
    override fun getMyComment(map: HashMap<String, String>, skip: Int, limit: Int): Observable<Data<MyComment>> {
        Observable.empty<MyComment>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in Data<MyComment>> ->
            try {
                val body = Gson().toJson(map)
                val response = RestApi().appService.getMyComment("activity,creator,activity.creator", body, skip, limit).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable("获取评论失败"))
                }
                subscriber.onCompleted()

            } catch (t: Throwable) {
                t.printStackTrace()
                subscriber.onError(Throwable("获取评论失败"))
                subscriber.onCompleted()
            }

        })
    }

    override fun getComment(activityPoint: Point, skip: Int, limit: Int): Observable<Data<Comment>> {
        Observable.empty<Comment>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in Data<Comment>> ->
            try {
                val map = HashMap<String, String>()
                map.put("activityId", activityPoint.objectId!!)
                val body = Gson().toJson(map)

                val response = RestApi().appService.getComment("activity,creator", body, skip, limit).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable("获取评论失败"))
                }
                subscriber.onCompleted()

            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("获取评论失败"))
                subscriber.onCompleted()
            }

        })
    }

    override fun followUser(followUserId: String, followerUserId: String): Observable<BaseEntity> {
        Observable.empty<BaseEntity>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseEntity> ->
            try {
                val response = RestApi().appService.follow(followUserId, followerUserId).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable("操作失败"))
                }
                subscriber.onCompleted()
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("操作失败"))
                subscriber.onCompleted()
            }
        })
    }

    override fun likeActivity(activityId: String, like: String): Observable<BaseResult> {
        Observable.empty<BaseResult>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseResult> ->
            try {

                val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), like)

                val response = RestApi().appService.like(activityId, body).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable("操作失败"))
                }
                subscriber.onCompleted()
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("操作失败"))
                subscriber.onCompleted()
            }
        })
    }

    override fun register(user: _User): Observable<_User> {
        Observable.empty<_User>().subscribe()
        return Observable.create { subscriber: Subscriber<in _User> ->
            try {
                val response = RestApi().appService.register(user).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(Throwable(response.errorBody().string().toString()))
                    subscriber.onCompleted()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("注册失败"))
            }
        }

    }

    override fun login(username: String, password: String): Observable<_User> {
        Observable.empty<Any>().subscribe()
        return Observable.create { subscriber: Subscriber<in _User> ->
            try {
                val response = RestApi().appService.login(username, password).execute()

                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(Throwable("用户名或密码错误"))
                    subscriber.onCompleted()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("登录失败"))
                subscriber.onCompleted()
            }
        }
    }

    override fun getActivities(include: String, skip: Int, limit: Int): Observable<Data<ActivityInfo>> {
        Observable.empty<Any>().subscribe()
        return Observable.create { subscriber: Subscriber<in Data<ActivityInfo>> ->
            try {
                val response = RestApi().appService.getActivity(include, skip, limit).execute()

                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(Throwable("获取活动失败"))
                    subscriber.onCompleted()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("获取活动失败"))
                subscriber.onCompleted()
            }
        }
    }
}