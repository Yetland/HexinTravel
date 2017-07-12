package com.yetland.crazy.core.api

import com.yetland.crazy.core.constant.DEFAULT_LIMIT
import com.yetland.crazy.core.entity.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @Name:           AppService
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

interface AppService {

    @GET("login")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<_User>

    @POST("users")
    fun register(@Body user: _User): Call<BaseResult>

    @GET("classes/Activity")
    fun getActivity(@Query("include") creator: String, @Query("skip") skip: Int, @Query("limit") limit: Int = DEFAULT_LIMIT): Call<Data<ActivityInfo>>

    @PUT("classes/Activity/{name}")
    fun like(@Path("name") objectId: String, @Body like: RequestBody): Call<BaseResult>

    @POST("classes/Follow")
    fun follow(@Query("followUserId") followUserId: String, @Query("followerUserId") followerUserId: String): Call<BaseEntity>

    @GET("classes/Comment")
    fun getComment(@Query("include") include: String, @Query("activity") activityPoint: Point, @Query("skip") skip: Int, @Query("limit") limit: Int = DEFAULT_LIMIT): Call<Data<Comment>>
}