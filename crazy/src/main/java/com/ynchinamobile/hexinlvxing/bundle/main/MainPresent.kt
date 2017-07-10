package com.ynchinamobile.hexinlvxing.bundle.main

import rx.functions.Action1

@Suppress("UNREACHABLE_CODE")
/**
 * @Name:           MainPresenter
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class MainPresent : MainContract.Presenter() {
    override fun getActivityModel() {
        rxManager.add(mModel?.getActivities()?.subscribe({
            activityInfoData ->
            mView!!.onComplete(activityInfoData)
        })
        { throwable ->
            mView!!.onError(throwable.message!!)
        }!!)
    }

}