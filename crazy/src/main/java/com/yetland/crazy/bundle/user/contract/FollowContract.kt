package com.yetland.crazy.bundle.user.contract

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.constant.DEFAULT_LIMIT
import com.yetland.crazy.core.entity.BaseResult
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.Follow
import rx.Observable

/**
 * @Name: FollowContract
 * @Author: yeliang
 * @Date: 2017/7/16
 */
class FollowContract {

    interface View : BaseView {
        fun getFollower(map: HashMap<String, Any>, page: Int)
        fun getFollowee(map: HashMap<String, Any>, page: Int)
        fun follow(follow: Follow)
        fun unFollow(objectId: String)

        fun getFollowerSuccess(data: Data<Follow>)
        fun getFolloweeSuccess(data: Data<Follow>)
        fun followSuccess(follow: Follow)
        fun unFollowSuccess()

        fun getFollowerFailed(msg: String)
        fun getFolloweeFailed(msg: String)
        fun followFailed(msg: String)
        fun unFollowFailed(msg: String)
    }

    interface Model : BaseModel {

        fun getFollower(where: String, page: Int): Observable<Data<Follow>>
        fun getFollowee(where: String, page: Int): Observable<Data<Follow>>
        fun follow(follow: Follow): Observable<Follow>
        fun unFollow(objectId: String): Observable<BaseResult>
    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun getFollower(where: String, page: Int)
        abstract fun getFollowee(where: String, page: Int)
        abstract fun follow(follow: Follow)
        abstract fun unFollow(objectId: String)
    }
}

class FollowModel : FollowContract.Model {
    override fun getFollower(where: String, page: Int): Observable<Data<Follow>> {
        return AppApiImpl().getFollow(where, "follower,user", "-createdAt",
                page * DEFAULT_LIMIT, DEFAULT_LIMIT)
                .compose(RxSchedulers.new_thread())
    }

    override fun getFollowee(where: String, page: Int): Observable<Data<Follow>> {
        return AppApiImpl().getFollow(where, "follower,user", "-createdAt",
                page * DEFAULT_LIMIT, DEFAULT_LIMIT)
                .compose(RxSchedulers.new_thread())
    }

    override fun follow(follow: Follow): Observable<Follow> {
        return AppApiImpl().follow(follow).compose(RxSchedulers.new_thread())
    }

    override fun unFollow(objectId: String): Observable<BaseResult> {
        return AppApiImpl().unFollow(objectId).compose(RxSchedulers.new_thread())
    }

}

class FollowPresenter constructor(model: FollowModel, view: FollowContract.View) : FollowContract.Presenter(model, view) {
    override fun onStart() {

    }

    override fun getFollower(where: String, page: Int) {
        rxManager.add(mModel.getFollower(where, page).subscribe({
            data: Data<Follow> ->
            mView.getFollowerSuccess(data)
        }, {
            t: Throwable ->
            mView.getFollowerFailed(t.message!!)
        }))
    }

    override fun getFollowee(where: String, page: Int) {
        rxManager.add(mModel.getFollowee(where, page).subscribe({
            data: Data<Follow> ->
            mView.getFolloweeSuccess(data)
        }, {
            t: Throwable ->
            mView.getFolloweeFailed(t.message!!)
        }))
    }

    override fun follow(follow: Follow) {
        rxManager.add(mModel.follow(follow).subscribe({
            result: Follow ->
            mView.followSuccess(result)
        }, {
            t: Throwable ->
            mView.followFailed(t.message!!)
        }))
    }

    override fun unFollow(objectId: String) {
        rxManager.add(mModel.unFollow(objectId).subscribe({
            mView.unFollowSuccess()
        }, {
            t: Throwable ->
            mView.unFollowFailed(t.message!!)
        }))
    }

}