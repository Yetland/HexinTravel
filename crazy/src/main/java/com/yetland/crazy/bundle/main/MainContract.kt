package com.yetland.crazy.bundle.main

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.Data
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
        fun onComplete(activityModel: Data<ActivityInfo>)
    }

    interface Model : BaseModel {
        // 获取首页数据
        fun getActivities(): Observable<Data<ActivityInfo>>
    }

    abstract class Presenter(model: Model, view: View) : BasePresenter<Model, View>(model, view) {

        abstract fun getActivityModel()

        override fun onStart() {

        }
    }
}

class MainPresent(model: MainModel, view: MainContract.View) : MainContract.Presenter(model,view) {
    override fun getActivityModel() {
        rxManager.add(mModel.getActivities().subscribe({
            activityInfoData ->
            mView.onComplete(activityInfoData)
        })
        { throwable ->
            mView.onError(throwable.message!!)
        })
    }

}

class MainModel: MainContract.Model {
    override fun getActivities(): Observable<Data<ActivityInfo>> {
        return AppApiImpl().getActivities("creator").compose(RxSchedulers.new_thread())
    }
}