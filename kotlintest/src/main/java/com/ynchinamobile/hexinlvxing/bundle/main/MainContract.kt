package com.ynchinamobile.hexinlvxing.bundle.main

import com.ynchinamobile.hexinlvxing.core.base.BaseModel
import com.ynchinamobile.hexinlvxing.core.base.BasePresenter
import com.ynchinamobile.hexinlvxing.core.base.BaseView
import com.ynchinamobile.hexinlvxing.core.entity.ActivityInfo
import com.ynchinamobile.hexinlvxing.core.entity.Data
import rx.Observable

/**
 * @Name:           MainContract
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
interface MainContract {
    interface View : BaseView {
        fun onLoading(msg: String)
        fun onError(msg: String)
        fun onComplete(activityModel : Data<ActivityInfo>)
    }

    interface Model : BaseModel {
        // 获取首页数据
        fun getActivities(): Observable<Data<ActivityInfo>>
    }

    abstract class Presenter : BasePresenter<Model, View>() {

        abstract fun getActivityModel()

        override fun onStart() {

        }
    }
}