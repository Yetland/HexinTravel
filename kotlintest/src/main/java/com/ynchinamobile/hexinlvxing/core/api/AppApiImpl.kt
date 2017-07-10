package com.ynchinamobile.hexinlvxing.core.api

import com.ynchinamobile.hexinlvxing.core.entity.ActivityInfo
import com.ynchinamobile.hexinlvxing.core.entity.Data
import rx.Observable
import rx.Subscriber
import java.io.IOException

/**
 * @Name:           AppApiImpl
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class AppApiImpl : AppApi{
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