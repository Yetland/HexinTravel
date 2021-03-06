package com.yetland.crazy.core.constant

/**
 * @Name:           Constant
 * @Author:         yeliang
 * @Date:           2017/7/5
 */

object SharedPreferencesConstant {
    val PREF_NAME = "appPref"
    val KEY_USER = "user"
    val KEY_FOLLOWER_LIST = "followList"
    val KEY_SESSION_TOKEN = "sessionToken"
    val LOGON_SUCCESS: Int = 102000
}

val DEFAULT_LIMIT = 10

object IntentRequestCode {
    val MAIN_TO_DETAIL = 1000
    val MAIN_TO_LOGIN = 1001
    val MAIN_TO_USER_DATA = 1002
    val REGISTER = 1003
}

object IntentResultCode {
    val LOG_OUT = 2001
    val LOG_IN = 2002
    val REGISTER_SUCCESS = 2003
    val MAIN_TO_DETAIL_RESULT = 2004
    val ACTIVITY_DELETE = 2005
}