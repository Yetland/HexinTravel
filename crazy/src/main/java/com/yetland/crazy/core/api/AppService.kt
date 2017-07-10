package com.yetland.crazy.core.api

import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Name:           AppService
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

interface AppService {

    @GET("login")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<User>

    @GET("users")
    fun register(@Body user: User) : Call<BaseEntity>

    @GET("classes/Activity")
    fun getActivity(@Query("include") creator: String): Call<Data<ActivityInfo>>

}