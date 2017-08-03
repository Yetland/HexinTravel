package com.yetland.crazy.bundle.user.mine

import cn.finalteam.rxgalleryfinal.rxbus.event.BaseResultEvent
import com.google.gson.Gson
import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.constant.DEFAULT_LIMIT
import com.yetland.crazy.core.entity.BaseResult
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.MyComment
import rx.Observable

/**
 * @Name:           MyCommentContract
 * @Author:         yeliang
 * @Date:           2017/7/14
 */
class MyCommentContract {
    interface View : BaseView {
        fun getMyComment(where: HashMap<String, String>, page: Int)
        fun getMyCommentSuccess(data: Data<MyComment>)
        fun failed(msg: String)

        fun deleteAllComment(userId: String)
        fun deleteSuccess()
        fun deleteFailed(msg: String)
    }

    interface Model : BaseModel {
        fun getMyComment(where: HashMap<String, String>, page: Int): Observable<Data<MyComment>>
        fun deleteAllComment(userId: String): Observable<BaseResult>
    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun getMyComment(where: HashMap<String, String>, page: Int)
        abstract fun deleteAllComment(userId: String)
    }
}

class MyCommentModel : MyCommentContract.Model {
    override fun deleteAllComment(userId: String): Observable<BaseResult> {
        val map = HashMap<String, String>()
        map.put("creatorId", userId)
        return AppApiImpl().deleteAllComment(Gson().toJson(map)).compose(RxSchedulers.io_main())
    }

    override fun getMyComment(where: HashMap<String, String>, page: Int): Observable<Data<MyComment>> {
        return AppApiImpl().getMyComment(where, page * DEFAULT_LIMIT, DEFAULT_LIMIT).compose(RxSchedulers.io_main())
    }
}

class MyCommentPresenter constructor(model: MyCommentModel, view: MyCommentContract.View) :
        MyCommentContract.Presenter(model, view) {
    override fun deleteAllComment(userId: String) {
        rxManager.add(mModel.deleteAllComment(userId).subscribe({
            mView.deleteSuccess()
        }, {
            t ->
            mView.deleteFailed(t.message!!)
        }))
    }

    override fun onStart() {

    }

    override fun getMyComment(where: HashMap<String, String>, page: Int) {
        rxManager.add(mModel.getMyComment(where, page).subscribe({
            result: Data<MyComment> ->
            mView.getMyCommentSuccess(result)
        }, {
            throwable: Throwable ->
            mView.failed(throwable.message!!)
        }))
    }
}