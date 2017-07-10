package com.ynchinamobile.hexinlvxing.core.api

import com.ynchinamobile.hexinlvxing.core.entity.ActivityInfo
import com.ynchinamobile.hexinlvxing.core.entity.Data
import rx.Observable

/**
 * @Name:           AppApi
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
interface AppApi{
    fun getActivities(include : String) : Observable<Data<ActivityInfo>>
}