package com.yetland.crazy.bundle.user.login

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity.User
import rx.Observable

/**
 * Created by yeliang on 2017/7/10.
 */
interface LoginContract {
    interface View : BaseView {
        fun login(username: String, password: String)
        fun loginSuccess(user: User)
        fun loginFailed(msg: String)
        fun goToRegister()
    }

    interface Model : BaseModel {
        fun login(username: String, password: String): Observable<User>
    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun login(username: String, password: String)
    }
}

class LoginPresenter(model: LoginContract.Model, view: LoginContract.View) : LoginContract.Presenter(model, view) {
    override fun onStart() {

    }

    override fun login(username: String, password: String) {
        rxManager.add(mModel.login(username, password).subscribe({
            user ->
            mView.loginSuccess(user)
        }, {
            throwable ->
            mView.loginFailed(throwable.message!!)
        }))
    }

}

class LoginModel : LoginContract.Model {
    override fun login(username: String, password: String): Observable<User> {
        return AppApiImpl().login(username, password).compose(RxSchedulers.new_thread())
    }

}