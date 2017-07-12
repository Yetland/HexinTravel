package com.yetland.crazy.bundle.main.holder

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.BaseResult
import rx.Observable

/**
 * @Name:           ActivityHolderContract
 * @Author:         yeliang
 * @Date:           2017/7/12
 */
interface ActivityHolderContract {

    interface View : BaseView {
        fun like(activityId: String, like: String)
        fun cancelLike(activityId: String, like: String)
        fun follow(followUserId: String, followerUserId: String)
        fun likeSuccess()
        fun cancelLikeSuccess()
        fun followSuccess()
        fun fail(errorMsg: String)
    }

    interface Model : BaseModel {
        fun like(activityId: String, like: String): Observable<BaseResult>
        fun cancelLike(activityId: String, like: String): Observable<BaseResult>
        fun follow(followUserId: String, followerUserId: String): Observable<BaseEntity>
    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun like(activityId: String, like: String)
        abstract fun cancelLike(activityId: String, like: String)
        abstract fun follow(followUserId: String, followerUserId: String)
    }
}

class ActivityHolderModel : ActivityHolderContract.Model {
    override fun like(activityId: String, like: String): Observable<BaseResult> {
        return AppApiImpl().likeActivity(activityId, like).compose(RxSchedulers.new_thread())
    }

    override fun cancelLike(activityId: String, like: String): Observable<BaseResult> {
        return AppApiImpl().likeActivity(activityId, like).compose(RxSchedulers.new_thread())
    }

    override fun follow(followUserId: String, followerUserId: String): Observable<BaseEntity> {
        return AppApiImpl().followUser(followUserId, followerUserId).compose(RxSchedulers.new_thread())
    }

}

class ActivityHolderPresenter constructor(model: ActivityHolderModel, view: ActivityHolderContract.View)
    : ActivityHolderContract.Presenter(model, view) {
    override fun onStart() {
    }

    override fun like(activityId: String, like: String) {
        rxManager.add(mModel.like(activityId, like).subscribe({
            mView.likeSuccess()
        }, {
            throwable: Throwable ->
            mView.fail(throwable.message!!)
        }))
    }

    override fun cancelLike(activityId: String, like: String) {
        rxManager.add(mModel.like(activityId, like).subscribe({
            mView.cancelLikeSuccess()
        }, {
            throwable: Throwable ->
            mView.fail(throwable.message!!)
        }))
    }

    override fun follow(followUserId: String, followerUserId: String) {
        rxManager.add(mModel.follow(followUserId, followerUserId).subscribe({
            mView.followSuccess()
        }, {
            throwable: Throwable ->
            mView.fail(throwable.message!!)
        }))
    }

}