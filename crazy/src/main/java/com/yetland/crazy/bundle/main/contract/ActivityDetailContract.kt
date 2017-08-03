package com.yetland.crazy.bundle.main.contract

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.constant.DEFAULT_LIMIT
import com.yetland.crazy.core.entity.*
import rx.Observable

/**
 * @Name: ActivityDetailContract
 * @Author: yeliang
 * @Date: 2017/7/12
 */
class ActivityDetailContract {

    interface View : BaseView {

        fun getActivity(activityId: String)
        fun getActivitySuccess(activityInfo: ActivityInfo)
        fun getActivityFailed(msg: String)

        fun getComment(map: HashMap<String, String>, page: Int)
        fun failed(msg: String)
        fun getCommentSuccess(data: Data<Comment>)

        fun writeComment(comment: CommitComment)
        fun writeCommentSuccess(result: BaseResult)
        fun writeCommentFailed(msg: String)

        fun updateActivity(activityId: String, where: String)
        fun updateActivitySuccess()
        fun updateActivityFailed(msg: String)

        fun deleteActivity(activityId: String)
        fun deleteActivitySuccess()
        fun deleteActivityFailed(msg: String)
    }

    interface Model : BaseModel {
        fun getActivity(activityId: String): Observable<ActivityInfo>
        fun getComment(map: HashMap<String, String>, page: Int): Observable<Data<Comment>>
        fun writeComment(comment: CommitComment): Observable<BaseResult>
        fun updateActivity(activityId: String, where: String): Observable<BaseResult>
        fun deleteActivity(activityId: String): Observable<BaseResult>

    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun getActivity(activityId: String)
        abstract fun getComment(map: HashMap<String, String>, page: Int)
        abstract fun writeComment(comment: CommitComment)
        abstract fun updateActivity(activityId: String, where: String)
        abstract fun deleteActivity(activityId: String)
    }
}

class ActivityDetailModel : ActivityDetailContract.Model {
    override fun getActivity(activityId: String): Observable<ActivityInfo> {
        return AppApiImpl()
                .getActivity(activityId, "creator,forwardActivity,forwardActivity.creator")
                .compose(RxSchedulers.io_main())
    }

    override fun deleteActivity(activityId: String): Observable<BaseResult> {
        return AppApiImpl().deleteActivity(activityId).compose(RxSchedulers.io_main())
    }

    override fun updateActivity(activityId: String, where: String): Observable<BaseResult> {
        return AppApiImpl().updateActivity(activityId, where).compose(RxSchedulers.io_main())
    }

    override fun writeComment(comment: CommitComment): Observable<BaseResult> {
        return AppApiImpl().writeComment(comment).compose(RxSchedulers.io_main())
    }

    override fun getComment(map: HashMap<String, String>, page: Int): Observable<Data<Comment>> {
        return AppApiImpl().getComment(map, page * DEFAULT_LIMIT, DEFAULT_LIMIT).compose(RxSchedulers.new_thread())
    }
}

class ActivityDetailPresenter constructor(model: ActivityDetailModel, view: ActivityDetailContract.View) :
        ActivityDetailContract.Presenter(model, view) {
    override fun getActivity(activityId: String) {
        rxManager.add(mModel.getActivity(activityId).subscribe({
            activityInfo ->
            mView.getActivitySuccess(activityInfo)
        }, {
            t ->
            mView.getActivityFailed(t.message!!)
        }))
    }

    override fun deleteActivity(activityId: String) {
        rxManager.add(mModel.deleteActivity(activityId).subscribe({
            mView.deleteActivitySuccess()
        }, {
            t ->
            mView.deleteActivityFailed(t.message!!)
        }))
    }

    override fun updateActivity(activityId: String, where: String) {
        rxManager.add(mModel.updateActivity(activityId, where).subscribe({
            mView.updateActivitySuccess()
        }, {
            t: Throwable ->
            mView.updateActivityFailed(t.message!!)
        }))
    }

    override fun writeComment(comment: CommitComment) {
        rxManager.add(mModel.writeComment(comment).subscribe(
                {
                    result: BaseResult ->
                    mView.writeCommentSuccess(result)
                },
                {
                    throwable: Throwable ->
                    mView.writeCommentFailed(throwable.message!!)
                }
        ))
    }

    override fun getComment(map: HashMap<String, String>, page: Int) {
        rxManager.add(mModel.getComment(map, page).subscribe(
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