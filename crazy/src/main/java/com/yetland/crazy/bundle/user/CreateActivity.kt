package com.yetland.crazy.bundle.user

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import cn.finalteam.rxgalleryfinal.RxGalleryFinal
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent
import com.yetland.crazy.bundle.user.contract.*
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.OnRecyclerViewItemClickListener
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.FileUtils
import com.yetland.crazy.core.utils.ImageUtils
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_create.*
import java.io.File


class CreateActivity : BaseActivity(), OnRecyclerViewItemClickListener, UploadImageContract.View,
        CreateActivityContract.View {

    val uploadImagePresenter = UploadImagePresenter(UploadImageModel(), this)
    val createActivityPresenter = CreateActivityPresenter(CreateActivityModel(), this)

    var avatarList = ArrayList<BaseEntity>()
    var imagePath = ArrayList<String>()
    var imageUrl = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        setSupportActionBar(toolbar2)
        supportActionBar?.title = "Add"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvPhoto.canLoadMore = false
        rvPhoto.canRefresh = false
        rvPhoto.layoutManager = GridLayoutManager(activity, 3)
        rvPhoto.initView(activity)
        rvPhoto.adapter.onItemClickListener = this

        val defaultAvatar = Avatar()
        defaultAvatar.avatarUrl = R.mipmap.add_img
        avatarList.add(defaultAvatar)
        rvPhoto.onDefaultComplete(avatarList, 0)

        ivCreate.setOnClickListener({
            val title = etTitle.text.trim()
            if (title.isEmpty()) {
                ToastUtils.showShortSafe("Title is null")
            } else if (imagePath.size == 0) {
                ToastUtils.showShortSafe("Please choose image")
            } else {
                imagePath.map { File(it) }
                        .forEach {
                            if (FileUtils.isFileExists(it)) {
                                uploadImage(it)
                            }
                        }
            }
        })
    }

    override fun onRecyclerViewItemClick(position: Int) {
        if (position == 0) {
            RxGalleryFinal.with(activity)
                    .hideCamera()
                    .image()
                    .multiple()
                    .maxSize(2)
                    .imageLoader(ImageLoaderType.PICASSO)
                    .subscribe(object : RxBusResultDisposable<ImageMultipleResultEvent>() {
                        override fun onEvent(t: ImageMultipleResultEvent) {
                            val result = t.result
                            for (m in result) {
                                LogUtils.e("size = ${m.length},path = ${m.originalPath}")
                                if (imagePath.size < 3 && !imagePath.contains(m.originalPath)) {
                                    val avatar = Avatar()
                                    imagePath.add(m.originalPath)
                                    avatar.type = Avatar.IMAGE_TYPE.STRING_PATH_RES
                                    avatar.avatarPath = m.originalPath
                                    avatarList.add(avatar)
                                }
                            }
                            rvPhoto.onDefaultComplete(avatarList, 0)
                        }

                        override fun onComplete() {
                            super.onComplete()
                        }

                    }).openGallery()
        }
    }

    override fun createActivity(activityInfo: CreateActivityInfo) {
        createActivityPresenter.createActivity(activityInfo)
    }

    override fun createActivitySuccess() {
        progressDialog.dismiss()
        ToastUtils.showShortSafe("createActivitySuccess")
        finish()
    }

    override fun createActivityFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe("createActivityFailed")
    }

    override fun compressImage(file: File) {
    }

    override fun compressImageSuccess(file: File) {
    }

    override fun compressImageFailed(msg: String) {
    }

    override fun uploadImage(file: File) {
        progressDialog.show()
        uploadImagePresenter.uploadImage(file)
    }

    override fun uploadImageFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe("uploadImageFailed")
    }

    override fun uploadImageSuccess(result: BaseResult) {
        if (imageUrl.size < imagePath.size) {
            imageUrl.add(result.url!!)
            if (imageUrl.size == imagePath.size) {
                val creator = Point("_User", currentLoginUser.objectId)
                var url = ""
                for (i in imageUrl) {
                    url = url + i + ";"
                }
                val activityInfo = CreateActivityInfo()
                activityInfo.creator = creator
                activityInfo.title = etTitle.text.toString().trim()
                activityInfo.url = url
                createActivity(activityInfo)
            }
        }
    }

}
