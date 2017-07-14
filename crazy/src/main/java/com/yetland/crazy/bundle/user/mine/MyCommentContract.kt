package com.yetland.crazy.bundle.user.mine

import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.constant.DEFAULT_LIMIT
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
    }

    interface Model : BaseModel {
        fun getMyComment(where: HashMap<String, String>, page: Int): Observable<Data<MyComment>>
    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {
        abstract fun getMyComment(where: HashMap<String, String>, page: Int)
    }
}

class MyCommentModel : MyCommentContract.Model {
    override fun getMyComment(where: HashMap<String, String>, page: Int): Observable<Data<MyComment>> {
        return AppApiImpl().getMyComment(where, page * DEFAULT_LIMIT, DEFAULT_LIMIT).compose(RxSchedulers.io_main())
    }
}

class MyCommentPresenter constructor(model: MyCommentModel, view: MyCommentContract.View) :
        MyCommentContract.Presenter(model, view) {
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