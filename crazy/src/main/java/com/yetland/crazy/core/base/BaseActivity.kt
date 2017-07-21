package com.yetland.crazy.core.base

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.yetland.crazy.bundle.user.login.LoginActivity
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity.Follow
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.ActivityManager
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.SharedPreferencesUtils

/**
 * @Name:           BaseActivity
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
abstract class BaseActivity : AppCompatActivity(), BaseMainView {
    var currentPage = 0
    lateinit var activity: Activity
    lateinit var progressDialog: MaterialDialog

    var isLogin = false
        get() {
            getCurrentUser()
            return isUserExist(currentLoginUser)
        }
    var isUserChanged = false
    var isFollowListChanged = false

    lateinit var currentLoginUser: _User
    lateinit var pref: SharedPreferences
    var followId = ArrayList<String>()
    var followList = ArrayList<Follow>()


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this

        pref = activity.getSharedPreferences(SharedPreferencesConstant.PREF_NAME, 0)
        pref.registerOnSharedPreferenceChangeListener(prefListener)

        getCurrentUser()
        getFollow()

        progressDialog = MaterialDialog.Builder(activity)
                .content("LOADING...")
                .progress(true, 0)
                .cancelable(false)
                .build()
        ActivityManager().addActivity(this)
    }

    val prefListener = SharedPreferences.OnSharedPreferenceChangeListener({
        sharedPreferences, s ->
        LogUtils.e("OnSharedPreferenceChangeListener , key = $s")
        when (s) {
            SharedPreferencesConstant.KEY_USER -> {
                onDataChanged()
                isUserChanged = true
                getCurrentUser()
            }
            SharedPreferencesConstant.KEY_FOLLOWER_LIST -> {
                onDataChanged()
                isFollowListChanged = true
                getFollow()
            }
        }
    })

    private fun getFollow() {
        followId = SharedPreferencesUtils.getFollowList()
        followList = SharedPreferencesUtils.getFollowLists()
    }

    private fun getCurrentUser() {
        currentLoginUser = SharedPreferencesUtils.getUserInfo()
        if (isUserExist(currentLoginUser)) {
            isLogin = true
        }
    }

    override fun onPause() {
        super.onPause()
        isFollowListChanged = false
        isUserChanged = false
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager().removeActivity(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showLogin() {
        activity.startActivityForResult(Intent(activity, LoginActivity::class.java), IntentRequestCode.MAIN_TO_LOGIN)
    }

    fun isUserExist(user: _User): Boolean {

        if (user.objectId.isNotEmpty() && user.username.isNotEmpty()) {
            return true
        }
        return false
    }

    override fun onDataChanged() {
        LogUtils.e("onDataChanged")
    }

    fun startActivity1(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }
}