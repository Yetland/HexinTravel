package com.yetland.crazy.bundle.user.contract

import com.yetland.crazy.core.base.BaseModel
import com.yetland.crazy.core.base.BasePresenter
import com.yetland.crazy.core.base.BaseView
import com.yetland.crazy.core.entity.BaseResult
import rx.Observable
import java.io.File
import rx.android.schedulers.AndroidSchedulers
import com.yetland.crazy.bundle.main.MainActivity
import top.zibin.luban.Luban
import android.support.annotation.NonNull
import com.yetland.crazy.core.api.AppApiImpl
import com.yetland.crazy.core.base.RxSchedulers
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.utils.Utils
import rx.Subscriber
import rx.schedulers.Schedulers
import rx.internal.operators.OperatorReplay.observeOn
import top.zibin.luban.OnCompressListener


/**
 * @Name:           UploadImageContract
 * @Author:         yeliang
 * @Date:           2017/7/28
 */
class UploadImageContract {

    interface View : BaseView {

        fun compressImage(file: File)
        fun compressImageSuccess(file: File)
        fun compressImageFailed(msg: String)

        fun uploadImage(file: File)
        fun uploadImageFailed(msg: String)
        fun uploadImageSuccess(result: BaseResult)

    }

    interface Model : BaseModel {
        fun compressImage(file: File): Observable<File>
        fun uploadImage(file: File): Observable<BaseResult>
    }

    abstract class Presenter constructor(model: Model, view: View) : BasePresenter<Model, View>(model, view) {

        abstract fun compressImage(file: File)
        abstract fun uploadImage(file: File)
    }

}

class UploadImageModel : UploadImageContract.Model {
    override fun compressImage(file: File): Observable<File> {
        return AppApiImpl().compressImage(file).compose(RxSchedulers.io_main())
    }

    override fun uploadImage(file: File): Observable<BaseResult> {
        return AppApiImpl().uploadImage(file).compose(RxSchedulers.new_thread())
    }

}

class UploadImagePresenter constructor(model: UploadImageModel, view: UploadImageContract.View) :
        UploadImageContract.Presenter(model, view) {
    override fun compressImage(file: File) {
        rxManager.add(mModel.compressImage(file).subscribe({
            imageFile ->
            mView.compressImageSuccess(imageFile)
        }, {
            t ->
            mView.compressImageFailed(t.message!!)
        }))
    }

    override fun uploadImage(file: File) {
        rxManager.add(mModel.uploadImage(file).subscribe({
            result ->
            mView.uploadImageSuccess(result)
        }, {
            t ->
            mView.uploadImageFailed(t.message!!)
        }))
    }

    override fun onStart() {

    }

}