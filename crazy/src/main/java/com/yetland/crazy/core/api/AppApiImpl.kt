package com.yetland.crazy.core.api

import android.util.Log
import com.google.gson.Gson
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import rx.Observable
import rx.Subscriber
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.io.IOException

/**
 * @Name:           AppApiImpl
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class AppApiImpl : AppApi {
    override fun deleteAllComment(where: String): Observable<BaseResult> {
        Observable.empty<BaseResult>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseResult> ->
            try {
                val response = RestApi().appService.deleteAll("Comment", where).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable(response.errorBody().string()))
                }
                subscriber.onCompleted()
            } catch (t: Throwable) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }
        })
    }

    override fun getActivity(objectId: String, include: String): Observable<ActivityInfo> {
        Observable.empty<ActivityInfo>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in ActivityInfo> ->
            try {
                val response = RestApi().appService.getActivity(objectId, include).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable(response.errorBody().string()))
                }
                subscriber.onCompleted()
            } catch (t: Throwable) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }
        })
    }

    override fun deleteActivity(activityId: String): Observable<BaseResult> {
        Observable.empty<BaseResult>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseResult> ->
            try {
                val response = RestApi().appService.deleteClass("Activity", activityId).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable(response.errorBody().string()))
                }
                subscriber.onCompleted()
            } catch (t: Throwable) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }
        })
    }

    override fun createActivity(createActivityInfo: CreateActivityInfo): Observable<BaseResult> {
        Observable.empty<BaseResult>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseResult> ->
            try {
                val response = RestApi().appService.createActivity(createActivityInfo).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable(response.errorBody().string()))
                }
                subscriber.onCompleted()

            } catch (t: Throwable) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }
        })
    }

    override fun updateUser(user: _User, where: String): Observable<BaseResult> {
        Observable.empty<BaseResult>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseResult> ->
            try {
                val sessionToken = SharedPreferencesUtils.getString(SharedPreferencesConstant.KEY_SESSION_TOKEN)

                if (sessionToken.isEmpty()) {
                    subscriber.onError(Throwable("User sessionToken is null"))
                    subscriber.onCompleted()
                } else {
                    val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), where)
                    val response = RestApi().appService.updateUser(sessionToken, user.objectId, body).execute()
                    if (response.isSuccessful) {
                        subscriber.onNext(response.body())
                    } else {
                        subscriber.onError(Throwable(response.errorBody().string()))
                    }
                    subscriber.onCompleted()
                }
            } catch (t: IOException) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }
        })
    }

    override fun uploadImage(file: File): Observable<BaseResult> {
        Observable.empty<File>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseResult> ->
            try {
                val response = RestApi().appService.
                        uploadImage(file.name,
                                RequestBody.create(MediaType.parse("image/*"), file)).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable(response.errorBody().string()))
                }
                subscriber.onCompleted()
            } catch (t: IOException) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }
        })
    }

    override fun compressImage(file: File): Observable<File> {
        Observable.empty<File>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in File> ->
            try {
                Luban.with(Utils.getContext())
                        .load(file)
                        .setCompressListener((object : OnCompressListener {
                            override fun onSuccess(compressFile: File?) {
                                subscriber.onNext(compressFile)
                                subscriber.onCompleted()
                            }

                            override fun onError(e: Throwable?) {
                                subscriber.onError(e)
                                subscriber.onCompleted()
                            }

                            override fun onStart() {

                            }
                        })).launch()
            } catch (t: IOException) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }
        })
    }

    override fun getUser(objectId: String): Observable<_User> {
        Observable.empty<_User>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in _User> ->
            try {

                val response = RestApi().appService.getUser(objectId).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable(response.errorBody().string()))
                }
                subscriber.onCompleted()

            } catch (t: Throwable) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }
        })
    }

    override fun getFollow(where: String, include: String, order: String, skip: Int, limit: Int): Observable<Data<Follow>> {
        Observable.empty<Follow>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in Data<Follow>> ->
            try {

                val response = RestApi().appService.getFollow(where, include, order, skip, limit).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable(response.errorBody().string()))
                }
                subscriber.onCompleted()

            } catch (t: Throwable) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }
        })
    }

    override fun follow(follow: CommitFollow): Observable<Follow> {
        Observable.empty<CommitFollow>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in Follow> ->

            try {
                val response = RestApi().appService.follow(follow).execute()
                if (response.isSuccessful) subscriber.onNext(response.body())
                else subscriber.onError(Throwable(response.errorBody().string()))
                subscriber.onCompleted()
            } catch (t: Throwable) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }

        })
    }

    override fun unFollow(objectId: String): Observable<BaseResult> {
        Observable.empty<BaseResult>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseResult> ->

            try {
                val response = RestApi().appService.deleteFollow(objectId).execute()
                if (response.isSuccessful) subscriber.onNext(response.body())
                else subscriber.onError(Throwable(response.errorBody().string()))
                subscriber.onCompleted()
            } catch (t: Throwable) {
                subscriber.onError(t)
                subscriber.onCompleted()
            }

        })
    }

    override fun writeComment(comment: CommitComment): Observable<BaseResult> {
        Observable.empty<BaseResult>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseResult> ->
            try {
                val body = Gson().toJson(comment)
                Log.e("writeComment", body)
                val response = RestApi().appService.writeComment(comment).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable(response.errorBody().string()))
                }
                subscriber.onCompleted()

            } catch (e: IOException) {
                subscriber.onError(Throwable("请求失败"))
                subscriber.onCompleted()
            }
        })
    }


    override fun getMyComment(map: HashMap<String, String>, skip: Int, limit: Int): Observable<Data<MyComment>> {
        Observable.empty<MyComment>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in Data<MyComment>> ->
            try {
                val where = Gson().toJson(map)
                val response = RestApi().appService.getMyComment("activity,creator,activity.creator",
                        where, "-createdAt", skip, limit).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable("获取评论失败"))
                }
                subscriber.onCompleted()

            } catch (t: Throwable) {
                t.printStackTrace()
                subscriber.onError(Throwable("获取评论失败"))
                subscriber.onCompleted()
            }

        })
    }

    override fun getComment(map: HashMap<String, String>, skip: Int, limit: Int): Observable<Data<Comment>> {
        Observable.empty<Comment>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in Data<Comment>> ->
            try {
                val where = Gson().toJson(map)

                val response = RestApi().appService.getComment("activity,creator,activity.creator", where,
                        "-createdAt", skip, limit).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable("获取评论失败"))
                }
                subscriber.onCompleted()

            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("获取评论失败"))
                subscriber.onCompleted()
            }

        })
    }

    override fun followUser(followUserId: String, followerUserId: String): Observable<BaseEntity> {
        Observable.empty<BaseEntity>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseEntity> ->
            try {
                val response = RestApi().appService.follow(followUserId, followerUserId).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable("操作失败"))
                }
                subscriber.onCompleted()
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("操作失败"))
                subscriber.onCompleted()
            }
        })
    }

    override fun updateActivity(activityId: String, where: String): Observable<BaseResult> {
        Observable.empty<BaseResult>().subscribe()
        return Observable.create({
            subscriber: Subscriber<in BaseResult> ->
            try {
                Log.e("updateActivity", where)
                val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), where)
                val response = RestApi().appService.updateActivity(activityId, body).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                } else {
                    subscriber.onError(Throwable("操作失败"))
                }
                subscriber.onCompleted()
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("操作失败"))
                subscriber.onCompleted()
            }
        })
    }

    override fun register(user: _User): Observable<_User> {
        Observable.empty<_User>().subscribe()
        return Observable.create { subscriber: Subscriber<in _User> ->
            try {
                val response = RestApi().appService.register(user).execute()
                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(Throwable(response.errorBody().string().toString()))
                    subscriber.onCompleted()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("注册失败"))
            }
        }

    }

    override fun login(username: String, password: String): Observable<_User> {
        Observable.empty<Any>().subscribe()
        return Observable.create { subscriber: Subscriber<in _User> ->
            try {
                val response = RestApi().appService.login(username, password).execute()

                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(Throwable("用户名或密码错误"))
                    subscriber.onCompleted()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("登录失败"))
                subscriber.onCompleted()
            }
        }
    }

    override fun getActivities(include: String, where: String?, skip: Int, limit: Int): Observable<Data<ActivityInfo>> {
        Observable.empty<Any>().subscribe()
        return Observable.create { subscriber: Subscriber<in Data<ActivityInfo>> ->
            try {
                val response = RestApi().appService.getActivity(include, where, "-likeCount,-createdAt", skip, limit).execute()

                if (response.isSuccessful) {
                    subscriber.onNext(response.body())
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(Throwable("获取活动失败"))
                    subscriber.onCompleted()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(Throwable("获取活动失败"))
                subscriber.onCompleted()
            }
        }
    }
}