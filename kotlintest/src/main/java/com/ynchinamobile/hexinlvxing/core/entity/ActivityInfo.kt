package com.ynchinamobile.hexinlvxing.core.entity

import java.io.Serializable

/**
 * @Name:           ActivityInfo
 * @Author:         yeliang
 * @Date:           2017/7/6
 */

class ActivityInfo : BaseEntity.BaseBean(), Serializable {

    var title: String? = null// 活动的标题
    var content: String? = null// 活动内容简介
}