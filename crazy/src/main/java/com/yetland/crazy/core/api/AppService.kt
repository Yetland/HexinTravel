package com.yetland.crazy.core.api

import com.yetland.crazy.core.constant.DEFAULT_LIMIT
import com.yetland.crazy.core.entity.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @Name:           AppService
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

interface AppService {

    /**
     * POST /classes/{class} 创建对象
     * PUT /classes/{class}/{objectId} 更新对象
     * GET /classes/{class}/{objectId} 获得单个对象
     * GET /classes/{class} 查询对象
     * DELETE /classes/{class}/{objectId} 删除对象
     */

    @GET("classes/Banner")
    fun getBanner(): Call<Data<Banner>>

    @DELETE("classes/{class}/{objectId}")
    fun deleteClass(@Path("class") className: String, @Path("objectId") objectId: String): Call<BaseResult>

    @DELETE("classes/{class}")
    fun deleteAll(@Path("class") className: String, @Query("where") where: String): Call<BaseResult>

    @GET("login")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<_User>

    @GET("classes/_User/{objectId}")
    fun getUser(@Path("objectId") objectId: String): Call<_User>

    @POST("users")
    fun register(@Body user: _User): Call<_User>

    @PUT("users/{uid}")
    fun updateUser(@Header("X-LC-Session") header: String,
                   @Path("uid") uid: String,
                   @Body body: RequestBody): Call<BaseResult>


    @GET("classes/Activity")
    fun getActivity(@Query("include") creator: String,
                    @Query("where") where: String? = "{}",
                    @Query("order") order: String = "-createdAt",
                    @Query("skip") skip: Int,
                    @Query("limit") limit: Int = DEFAULT_LIMIT): Call<Data<ActivityInfo>>

    @GET("classes/Activity/{activityId}")
    fun getActivity(@Path("activityId") activityId: String,
                    @Query("include") creator: String): Call<ActivityInfo>

    @PUT("classes/Activity/{name}")
    fun updateActivity(@Path("name") objectId: String,
                       @Body body: RequestBody): Call<BaseResult>

    @POST("classes/Activity")
    fun createActivity(@Body createActivityInfo: CreateActivityInfo): Call<BaseResult>

    @POST("classes/Follow")
    fun follow(@Query("followUserId") followUserId: String,
               @Query("followerUserId") followerUserId: String): Call<BaseEntity>

    @GET("classes/Comment")
    fun getComment(@Query("include") include: String,
                   @Query("where") where: String,
                   @Query("order") order: String,
                   @Query("skip") skip: Int,
                   @Query("limit") limit: Int): Call<Data<Comment>>

    @GET("classes/Comment")
    fun getMyComment(@Query("include") include: String,
                     @Query("where") where: String,
                     @Query("order") order: String,
                     @Query("skip") skip: Int,
                     @Query("limit") limit: Int): Call<Data<MyComment>>

    @POST("classes/Comment")
    fun writeComment(@Body comment: CommitComment): Call<BaseResult>


    @POST("classes/follower?fetchWhenSave=true")
    fun follow(@Body follow: CommitFollow): Call<Follow>

    @GET("classes/follower")
    fun getFollow(@Query("where") where: String,
                  @Query("include") include: String,
                  @Query("order") order: String,
                  @Query("skip") skip: Int,
                  @Query("limit") limit: Int): Call<Data<Follow>>

    @DELETE("classes/follower/{id}")
    fun deleteFollow(@Path("id") id: String): Call<BaseResult>

    @Headers("Content-Type: image/png")
    @POST("files/{name}")
    fun uploadImage(@Path("name") name: String, @Body body: RequestBody): Call<BaseResult>

}