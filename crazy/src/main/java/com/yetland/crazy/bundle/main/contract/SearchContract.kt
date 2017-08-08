package com.yetland.crazy.bundle.main.contract

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity.Banner
import com.yetland.crazy.core.entity.Data
import rx.Observable

/**
 * @Name: SearchContract
 * @Author: yeliang
 * @Date: 2017/8/8
 */
interface SearchContract {
    interface View : BaseView {
        fun getBanner()
        fun getBannerSuccess(data: Data<Banner>)
        fun getBannerFailed(msg: String)
    }

    interface Model : BaseModel {
        fun getBanner(): Observable<Data<Banner>>
    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun getBanner()
    }
}

class SearchModel : SearchContract.Model {
    override fun getBanner(): Observable<Data<Banner>> {
        return AppApiImpl().getBanner().compose(RxSchedulers.io_main())
    }
}

class SearchPresenter constructor(model: SearchModel, view: SearchContract.View) : SearchContract.Presenter(model, view) {
    override fun onStart() {
    }

    override fun getBanner() {
        rxManager.add(mModel.getBanner().subscribe({
            t ->
            mView.getBannerSuccess(t)
        }, {
            t ->
            mView.getBannerFailed(t.message!!)
        }))
    }

}