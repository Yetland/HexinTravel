package com.ynchinamobile.hexinlvxing.core.api

import com.ynchinamobile.hexinlvxing.core.entity.ActivityInfo
import com.ynchinamobile.hexinlvxing.core.entity.BaseEntity
import com.ynchinamobile.hexinlvxing.core.entity.Data
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @Name:           AppService
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

interface AppService {
    @GET("classes/Activity")
    fun getActivity(@Query("include") creator: String): Call<Data<ActivityInfo>>
}