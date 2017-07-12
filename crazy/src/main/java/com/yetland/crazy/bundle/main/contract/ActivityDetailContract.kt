package com.yetland.crazy.bundle.main.contract

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.constant.DEFAULT_LIMIT
import com.yetland.crazy.core.entity.Comment
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.Point
import rx.Observable

/**
 * @Name: ActivityDetailContract
 * @Author: yeliang
 * @Date: 2017/7/12
 */
class ActivityDetailContract {

    interface View : BaseView {

        fun getComment(activityPointer: Point, page: Int)
        fun failed(msg: String)
        fun getCommentSuccess(data: Data<Comment>)
    }

    interface Model : BaseModel {
        fun getComment(activityPointer: Point, page: Int): Observable<Data<Comment>>

    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun getComment(activityPointer: Point, page: Int)
    }

}

class ActivityDetailModel : ActivityDetailContract.Model {
    override fun getComment(activityPointer: Point, page: Int): Observable<Data<Comment>> {
        return AppApiImpl().getComment(activityPointer, page * DEFAULT_LIMIT, DEFAULT_LIMIT).compose(RxSchedulers.new_thread())
    }
}

class ActivityDetailPresenter constructor(model: ActivityDetailModel, view: ActivityDetailContract.View) :
        ActivityDetailContract.Presenter(model, view) {
    override fun getComment(activityPointer: Point, page: Int) {
        rxManager.add(mModel.getComment(activityPointer, page).subscribe(
                {
                    result: Data<Comment> ->
                    mView.getCommentSuccess(result)
                },
                {
                    throwable: Throwable ->
                    mView.failed(throwable.message!!)

                }
        ))
    }

    override fun onStart() {

    }

}