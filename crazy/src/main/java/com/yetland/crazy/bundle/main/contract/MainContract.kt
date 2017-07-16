package com.yetland.crazy.bundle.main.contract

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.constant.DEFAULT_LIMIT
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.Data
import rx.Observable

/**
 * @Name:           MainContract
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
interface MainContract {
    interface View : BaseView {
        fun getActivities(where: String?, skip: Int)
        fun onLoading(msg: String)
        fun onError(msg: String)
        fun onComplete(activityModel: Data<ActivityInfo>)
    }

    interface Model : BaseModel {
        // 获取首页数据
        fun getActivities(where: String?, skip: Int): Observable<Data<ActivityInfo>>
    }

    abstract class Presenter(model: Model, view: View) : BasePresenter<Model, View>(model, view) {

        abstract fun getActivities(where: String?, skip: Int)

        override fun onStart() {

        }
    }
}

class MainPresent(model: MainModel, view: MainContract.View) : MainContract.Presenter(model, view) {
    override fun getActivities(where: String?, skip: Int) {
        rxManager.add(mModel.getActivities(where, skip).subscribe({
            activityInfoData ->
            mView.onComplete(activityInfoData)
        })
        { throwable ->
            mView.onError(throwable.message!!)
        })
    }

}

class MainModel : MainContract.Model {
    override fun getActivities(where: String?, skip: Int): Observable<Data<ActivityInfo>> {
        return AppApiImpl().getActivities("creator", where, skip * DEFAULT_LIMIT, DEFAULT_LIMIT).compose(RxSchedulers.new_thread())
    }
}