package com.ynchinamobile.hexinlvxing.core.entity

import rx.Observable
import java.io.Serializable

/**
 * @Name:           BaseEntity
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
interface BaseEntity {
    open class BaseBean : Serializable {
        var objectId: String? = null
        var updatedAt: String? = null
        var createdAt: String? = null
        var url: String? = null
    }

    interface IListBean {
        fun getPageAt(page: Int): Observable<*>

        fun setParam(params: Map<String, String>)
    }

     abstract class ListBean : IListBean , BaseBean() {
         override fun setParam(params: Map<String, String>) {
         }
     }
}