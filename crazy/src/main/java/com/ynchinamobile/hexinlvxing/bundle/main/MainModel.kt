package com.ynchinamobile.hexinlvxing.bundle.main

import com.ynchinamobile.hexinlvxing.core.api.AppApiImpl
import com.ynchinamobile.hexinlvxing.core.base.RxSchedulers
import com.ynchinamobile.hexinlvxing.core.entity.ActivityInfo
import com.ynchinamobile.hexinlvxing.core.entity.Data
import rx.Observable

/**
 * @Name:           MainModel
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class MainModel: MainContract.Model{
    override fun getActivities(): Observable<Data<ActivityInfo>> {
        return AppApiImpl().getActivities("creator").compose(RxSchedulers.new_thread())
    }
}