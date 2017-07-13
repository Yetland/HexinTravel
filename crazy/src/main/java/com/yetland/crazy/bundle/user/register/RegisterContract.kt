package com.yetland.crazy.bundle.user.register

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity._User
import rx.Observable

/**
 * @Name:           RegisterContract
 * @Author:         yeliang
 * @Date:           2017/7/12
 */
class RegisterContract {

    interface View : BaseView {
        fun register(user: _User)
        fun success(resultUser: _User)
        fun failed(msg: String)
    }

    interface Model : BaseModel {
        fun register(user: _User): Observable<_User>
    }

    abstract class Presenter constructor(model: Model, view: View) :
            BasePresenter<Model, View>(model, view) {
        abstract fun register(user: _User)
    }
}

class RegisterModel : RegisterContract.Model {
    override fun register(user: _User): Observable<_User> {
        return AppApiImpl().register(user).compose(RxSchedulers.new_thread())
    }
}

class RegisterPresenter constructor(registerModel: RegisterModel, view: RegisterContract.View) :
        RegisterContract.Presenter(registerModel, view) {
    override fun onStart() {

    }

    override fun register(user: _User) {
        rxManager.add(mModel.register(user).subscribe({
            resultUser: _User ->
            mView.success(resultUser)
        }, {
            t: Throwable ->
            mView.failed(t.message!!)
        }))
    }

}