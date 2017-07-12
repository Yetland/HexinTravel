package com.yetland.crazy.bundle.user.register

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity.BaseResult
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
        fun success()
        fun failed(msg: String)
    }

    interface Model : BaseModel {
        fun register(user: _User): Observable<BaseResult>
    }

    abstract class Presenter constructor(model: Model, view: View) :
            BasePresenter<Model, View>(model, view) {
        abstract fun register(user: _User)
    }
}

class RegisterModel : RegisterContract.Model {
    override fun register(user: _User): Observable<BaseResult> {
        return AppApiImpl().register(user).compose(RxSchedulers.new_thread())
    }
}

class RegisterPresenter constructor(registerModel: RegisterModel, view: RegisterContract.View) :
        RegisterContract.Presenter(registerModel, view) {
    override fun onStart() {

    }

    override fun register(user: _User) {
        rxManager.add(mModel.register(user).subscribe({
            mView.success()
        }, {
            t: Throwable ->
            mView.failed(t.message!!)
        }))
    }

}