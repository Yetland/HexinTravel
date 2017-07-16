package com.yetland.crazy.core.api

/**
 * @Name: CommenApi
 * @Author: yeliang
 * @Date: 2017/7/15
 */
interface CommenApi<in BaseEntity> {
    /**
     * POST /classes/{class} 创建对象
     * PUT /classes/{class}/{objectId} 更新对象
     * GET /classes/{class}/{objectId} 获得单个对象
     * GET /classes/{class} 查询对象
     * DELETE /classes/{class}/{objectId} 删除对象
     */

    fun postData(t: BaseEntity)
    fun putData(t: BaseEntity, objectId: String)
    fun getData(t: BaseEntity)
    fun getData(t: BaseEntity, objectId: String)
    fun deleteData(t: BaseEntity, objectId: String)
}