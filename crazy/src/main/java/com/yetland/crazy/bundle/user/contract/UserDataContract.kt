package com.yetland.crazy.bundle.user.contract

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity._User
import rx.Observable

/**
 * @Name: UserDataContract
 * @Author: yeliang
 * @Date: 2017/7/16
 */
class UserDataContract {
    interface View : BaseView {
        fun getUser(objectId: String)
        fun getUserFailed(msg: String)
        fun getUserSuccess(user: _User)
    }

    interface Model : BaseModel {
        fun getUser(objectId: String): Observable<_User>
    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun getUser(objectId: String)
    }
}

class UserDataModel : UserDataContract.Model {
    override fun getUser(objectId: String): Observable<_User> {
        return AppApiImpl().getUser(objectId).compose(RxSchedulers.new_thread())
    }
}

class UserDataPresenter constructor(model: UserDataModel, view: UserDataContract.View) : UserDataContract.Presenter(model, view) {
    override fun onStart() {

    }

    override fun getUser(objectId: String) {
        rxManager.add(mModel.getUser(objectId).subscribe({
            user: _User ->
            mView.getUserSuccess(user)
        }, {
            t: Throwable ->
            mView.getUserFailed(t.message!!)
        }))
    }

}