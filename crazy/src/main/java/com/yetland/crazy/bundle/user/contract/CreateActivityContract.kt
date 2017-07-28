package com.yetland.crazy.bundle.user.contract

import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView

/**
 * @Name:           CreateActivityContract
 * @Author:         yeliang
 * @Date:           2017/7/28
 */
class CreateActivityContract {

    interface View : BaseView {

    }

    interface Model : BaseModel {

    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {

    }
}

class CreateActivityModel : CreateActivityContract.Model {

}

class CreateActivityPresenter constructor(model: CreateActivityModel, view: CreateActivityContract.View) :
        CreateActivityContract.Presenter(model, view) {
    override fun onStart() {

    }

}