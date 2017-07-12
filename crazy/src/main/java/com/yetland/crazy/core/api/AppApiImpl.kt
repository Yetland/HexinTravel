package com.yetland.crazy.core.api

import com.yetland.crazy.core.entity.*
import rx.Observable
import rx.Subscriber
import java.io.IOException
import okhttp3.RequestBody

/**
 * @Name:           AppApiImpl
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class AppApiImpl : AppApi {
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

    override fun register(user: User): Observable<BaseResult> {
        Observable.empty<User>().subscribe()
        return Observable.create { subscriber: Subscriber<in BaseResult> ->
            try {
                val response = RestApi().appService.register(user).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(Throwable("用户名已存在"));
                    subscriber.onCompleted()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("注册失败"))
            }
        }

    }

    override fun login(username: String, password: String): Observable<User> {
        Observable.empty<Any>().subscribe()
        return Observable.create { subscriber: Subscriber<in User> ->
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