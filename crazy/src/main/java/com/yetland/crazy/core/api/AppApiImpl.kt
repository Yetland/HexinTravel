package com.yetland.crazy.core.api

import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.User
import rx.Observable
import rx.Subscriber
import java.io.IOException


/**
 * @Name:           AppApiImpl
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class AppApiImpl : AppApi {
    override fun register(user: User): Observable<BaseEntity> {
        Observable.empty<User>().subscribe()
        return Observable.create { subscriber: Subscriber<in BaseEntity> ->
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

    override fun getActivities(include: String): Observable<Data<ActivityInfo>> {
        Observable.empty<Any>().subscribe()
        return Observable.create { subscriber: Subscriber<in Data<ActivityInfo>> ->
            try {
                val response = RestApi().appService.getActivity(include).execute()

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