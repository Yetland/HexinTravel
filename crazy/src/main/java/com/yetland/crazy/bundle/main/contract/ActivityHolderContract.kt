package com.yetland.crazy.bundle.main.contract

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.BaseResult
import com.yetland.crazy.core.entity.CreateActivityInfo
import rx.Observable

/**
 * @Name:           ActivityHolderContract
 * @Author:         yeliang
 * @Date:           2017/7/12
 */
interface ActivityHolderContract {

    interface View : BaseView {
        fun like(activityId: String, where: String)
        fun likeSuccess()
        fun fail(errorMsg: String)
        fun forward(createActivityInfo: CreateActivityInfo)
        fun forwardSuccess()
        fun forwardFailed(msg: String)
    }

    interface Model : BaseModel {
        fun like(activityId: String, where: String): Observable<BaseResult>
        fun forward(createActivityInfo: CreateActivityInfo): Observable<BaseResult>
    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun like(activityId: String, where: String)
        abstract fun forward(createActivityInfo: CreateActivityInfo)
    }
}

class ActivityHolderModel : ActivityHolderContract.Model {
    override fun forward(createActivityInfo: CreateActivityInfo): Observable<BaseResult> {
        return AppApiImpl().createActivity(createActivityInfo).compose(RxSchedulers.io_main())
    }

    override fun like(activityId: String, where: String): Observable<BaseResult> {
        return AppApiImpl().updateActivity(activityId, where).compose(RxSchedulers.new_thread())
    }
}

class ActivityHolderPresenter constructor(model: ActivityHolderModel, view: ActivityHolderContract.View)
    : ActivityHolderContract.Presenter(model, view) {
    override fun forward(createActivityInfo: CreateActivityInfo) {
        rxManager.add(mModel.forward(createActivityInfo).subscribe({
            mView.forwardSuccess()
        }, {
            t ->
            if (t.message != null)
                mView.forwardFailed(t.message!!)
            else
                mView.forwardFailed("forwardFailed")
        }))
    }

    override fun onStart() {
    }

    override fun like(activityId: String, where: String) {
        rxManager.add(mModel.like(activityId, where).subscribe({
            mView.likeSuccess()
        }, {
            throwable: Throwable ->
            mView.fail(throwable.message!!)
        }))
    }
}