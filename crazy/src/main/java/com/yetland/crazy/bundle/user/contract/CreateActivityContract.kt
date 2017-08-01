package com.yetland.crazy.bundle.user.contract

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseResult
import com.yetland.crazy.core.entity.CreateActivityInfo
import rx.Observable

/**
 * @Name:           CreateActivityContract
 * @Author:         yeliang
 * @Date:           2017/7/28
 */
class CreateActivityContract {

    interface View : BaseView {
        fun createActivity(activityInfo: CreateActivityInfo)
        fun createActivitySuccess()
        fun createActivityFailed(msg: String)
    }

    interface Model : BaseModel {
        fun createActivity(activityInfo: CreateActivityInfo): Observable<BaseResult>

    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun createActivity(activityInfo: CreateActivityInfo)
    }
}

class CreateActivityModel : CreateActivityContract.Model {
    override fun createActivity(activityInfo: CreateActivityInfo): Observable<BaseResult> {
        return AppApiImpl().createActivity(activityInfo).compose(RxSchedulers.io_main())
    }

}

class CreateActivityPresenter constructor(model: CreateActivityModel, view: CreateActivityContract.View) :
        CreateActivityContract.Presenter(model, view) {
    override fun createActivity(activityInfo: CreateActivityInfo) {
        rxManager.add(mModel.createActivity(activityInfo).subscribe({
            mView.createActivitySuccess()
        }, {
            t ->
            mView.createActivityFailed(t.message!!)
        }))
    }

    override fun onStart() {

    }

}