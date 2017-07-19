package com.yetland.crazy.core.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yetland.crazy.core.constant.SharedPrefrencesConstant
import com.yetland.crazy.core.entity.Follow
import com.yetland.crazy.core.entity._User
import rx.Observable


/**
 * Created by yeliang on 2017/7/10.
 */

object SharedPreferencesUtils {


    fun cleanData(context: Context = Utils.getContext(), fileName: String) {
        val editor = context.getSharedPreferences(fileName, 0).edit()
        editor.clear()
        editor.apply()
    }

    fun clearUserInfo(context: Context = Utils.getContext()) {
        val clientPreferences = context.getSharedPreferences(SharedPrefrencesConstant.FILE_KEY_USER, 0)
        val prefEditor = clientPreferences.edit()
        prefEditor.clear()
        prefEditor.apply()
    }

    fun saveUserInfo(context: Context = Utils.getContext(), user: _User) {
        val clientPreferences = context.getSharedPreferences(SharedPrefrencesConstant.FILE_KEY_USER, 0)
        val prefEditor = clientPreferences.edit()
        prefEditor.putString(SharedPrefrencesConstant.FILE_KEY_USER, Gson().toJson(user))
        prefEditor.apply()
    }

    fun getUserInfo(context: Context = Utils.getContext()): _User {
        val clientPreferences = context.getSharedPreferences(SharedPrefrencesConstant.FILE_KEY_USER, 0)
        val userString = clientPreferences.getString(SharedPrefrencesConstant.FILE_KEY_USER, "")
        if (userString.isEmpty()) {
            return _User()
        }
        return Gson().fromJson(userString, _User::class.java)
    }

    fun saveString(key: String, content: String) {
        val clientPreferences = Utils.getContext().getSharedPreferences(SharedPrefrencesConstant.PREF_NAME, 0)
        val prefEditor = clientPreferences.edit()
        prefEditor.putString(key, content)
        prefEditor.apply()
    }

    fun getString(key: String): String {
        val clientPreferences = Utils.getContext().getSharedPreferences(SharedPrefrencesConstant.PREF_NAME, 0)
        val result = clientPreferences.getString(key, "")
        return result
    }

    fun getFollowList(): ArrayList<String> {
        val followId = ArrayList<String>()
        val followString = SharedPreferencesUtils.getString(SharedPrefrencesConstant.KEY_FOLLOWER_LIST)
        if (followString.isNotEmpty()) {
            val type = object : TypeToken<List<Follow>>() {}.type
            val followList: ArrayList<Follow> = Gson().fromJson(followString, type)
            Observable.from(followList)
                    .map({ follow -> follow.user.objectId })
                    .subscribe({ id -> followId.add(id) })
        }
        return followId
    }

    fun getFollowLists(): ArrayList<Follow> {
        var followList = ArrayList<Follow>()
        val followString = SharedPreferencesUtils.getString(SharedPrefrencesConstant.KEY_FOLLOWER_LIST)
        if (followString.isNotEmpty()) {
            val type = object : TypeToken<List<Follow>>() {}.type
            followList = Gson().fromJson(followString, type)
        }
        return followList
    }
}

