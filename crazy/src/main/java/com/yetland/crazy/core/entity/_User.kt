package com.yetland.crazy.core.entity

import com.yetland.crazy.bundle.destination.bean.TypeFactory

/**
 * Created by yeliang on 2017/7/10.
 */
class _User : BaseEntity() {
    override fun type(typeFactory: TypeFactory): Int {
        // TODO
        return 1
    }

    var avatarUrl: String? = ""
    var sessionToken: String? = null
    var authData: String? = null
    var username: String? = ""
    var password: String? = ""
    var mobilePhoneNumber: String? = null
    var mobilePhoneVerified: Boolean = false
    var email: String? = ""
    var emailVerified: Boolean = false
    var sex: String? = null
    var sign: String? = null
    var schoolName: String? = null
    var graduateYear: String? = null
    var className: String? = null
}