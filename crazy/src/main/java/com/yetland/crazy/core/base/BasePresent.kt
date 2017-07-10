package com.yetland.crazy.core.base

/**
 * @Name:           BasePresent
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BasePresenter<M, V> constructor(m: M, v: V) {

    var mModel: M
    var mView: V
    var rxManager = RxManager()

    init {
        mModel = m
        mView = v
    }

    abstract fun onStart()

    fun onDestroy() {
        rxManager.clear()
    }
}
