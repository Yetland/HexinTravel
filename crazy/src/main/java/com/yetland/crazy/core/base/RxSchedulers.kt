package com.yetland.crazy.core.base

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @Name:           RxSchedulers
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
object RxSchedulers {
    fun <T> io_main(): Observable.Transformer<T, T> {
        return Observable.Transformer<T, T> {
            tObservable ->
            tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> new_thread(): Observable.Transformer<T, T> {
        return Observable.Transformer<T, T> {
            tObservable ->
            tObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}
