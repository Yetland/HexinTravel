package com.ynchinamobile.hexinlvxing.core.base

/**
 * @Name:           BasePresent
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BasePresenter<E, T> {

    var mModel: E? = null
    var mView: T? = null
    var rxManager = RxManager()

    fun setVM(v: T, m: E) {
        this.mView = v
        this.mModel = m
        this.onStart()
    }

    abstract fun onStart()

    fun onDestroy() {
        rxManager.clear()
    }
}
