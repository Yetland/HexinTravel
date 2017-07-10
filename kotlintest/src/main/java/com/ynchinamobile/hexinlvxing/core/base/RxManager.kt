package com.ynchinamobile.hexinlvxing.core.base

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription
import java.util.HashMap

/**
 * @Name:           RxManager
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

open class RxManager {

    var mRxBus = RxBus().newInstance()
    private val observables = HashMap<String, Observable<*>>()
    private val compositeSubscription = CompositeSubscription()

    fun on(eventName: String, action1: Action1<Any>) {
        val observable = mRxBus.register<Any>(eventName)
        observables.put(eventName, observable)
        compositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, Action1<Throwable> { it.printStackTrace() }))
    }

    fun add(subscription: Subscription) {
        compositeSubscription.add(subscription)
    }

    fun clear() {
        compositeSubscription.unsubscribe()
        for ((key, value) in observables) {
            mRxBus.unregister(key, value)
        }
    }

    fun post(tag: Any, content: Any) {
        mRxBus.post(tag, content)
    }
}